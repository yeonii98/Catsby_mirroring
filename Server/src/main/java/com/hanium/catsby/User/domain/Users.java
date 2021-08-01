package com.hanium.catsby.User.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String address;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<BowlLike> bowlLikes = new ArrayList<>();

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<BowlCommunity> bowlCommunities = new ArrayList<>();

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<BowlComment> bowlComments = new ArrayList<>();

}
