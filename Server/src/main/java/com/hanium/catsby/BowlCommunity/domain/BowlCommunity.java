package com.hanium.catsby.BowlCommunity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanium.catsby.Bowl.domain.Bowl;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Bowl_Community")
public class BowlCommunity {

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

    @Column(name = "created_time")
    private LocalDateTime createDate;

    @Column(name = "updated_time")
    private LocalDateTime updateDate;

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

    public void setCreateDate(){
        this.createDate = LocalDateTime.now();
    }

    public void setUpdateDate(){
        this.updateDate = LocalDateTime.now();
    }
}
