package com.hanium.catsby.bowl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanium.catsby.util.BaseTimeEntity;
import com.hanium.catsby.user.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Bowl_Community")
public class BowlCommunity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowlCommunity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_id")
    private Bowl bowl;

    @Lob
    private byte[] image;

    @Lob
    private String content;

    @OneToMany(mappedBy = "bowlCommunity", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"bowlCommunity"})
    private List<BowlComment> bowlComments = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "bowlCommunity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BowlLike bowlLike;

    public void setUser(Users user){
        this.user = user;
        user.getBowlCommunities().add(this);
    }

    public void setBowl(Bowl bowl){
        this.bowl = bowl;
        bowl.getBowlCommunities().add(this);
    }
}
