package com.hanium.catsby.BowlLike.domain;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class BowlLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users user;

    @OneToOne(mappedBy = "bowlLike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BowlCommunity bowlCommunity;


}
