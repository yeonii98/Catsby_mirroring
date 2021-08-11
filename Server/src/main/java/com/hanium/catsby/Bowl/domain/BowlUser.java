package com.hanium.catsby.Bowl.domain;

import com.hanium.catsby.Bowl.domain.Bowl;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "Bowl_User")
public class BowlUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_id")
    private Bowl bowl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

}
