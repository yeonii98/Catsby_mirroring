package com.hanium.catsby.domain.bowl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanium.catsby.domain.common.model.BaseTimeEntity;
import com.hanium.catsby.domain.user.model.Users;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;

    private String uid;

}
