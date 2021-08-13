package com.hanium.catsby.bowl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.util.BaseTimeEntity;
import com.hanium.catsby.user.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "Bowl_Like")
public class BowlLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowlLike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;

    public void setBowlCommunity(BowlCommunity bowlCommunity){
        this.bowlCommunity = bowlCommunity;
    }

    public void setUser(Users user){
        this.user = user;
    }
}
