package com.hanium.catsby.domain.notification.model;

import com.hanium.catsby.domain.common.model.BaseTimeEntity;
import com.hanium.catsby.domain.user.model.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private String message;
}
