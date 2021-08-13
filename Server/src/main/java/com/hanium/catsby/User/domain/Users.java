package com.hanium.catsby.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.bowl.domain.BowlComment;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.domain.BowlLike;
import com.hanium.catsby.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Users extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String uid;
    private String nickname;
    private String email;
    private String address;

    @Column(name = "fcm_token")
    private String fcmToken;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BowlLike> bowlLikes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BowlCommunity> bowlCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BowlComment> bowlComments = new ArrayList<>();
}
