package com.hanium.catsby.domain.bowl.model;

import com.hanium.catsby.domain.common.model.BaseTimeEntity;
import com.hanium.catsby.domain.user.model.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Bowl_User")
@NoArgsConstructor
public class BowlUser extends BaseTimeEntity {

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

    private String image;

    public BowlUser(Bowl bowl, Users user) {
        this.bowl = bowl;
        this.user = user;
    }
}
