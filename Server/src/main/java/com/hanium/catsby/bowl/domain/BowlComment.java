package com.hanium.catsby.bowl.domain;

import com.hanium.catsby.util.BaseTimeEntity;
import com.hanium.catsby.user.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "Bowl_Comment")
public class BowlComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowlComment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;

    @Lob
    private String content;

}
