package com.hanium.catsby.bowl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowlCommunity_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BowlCommunity bowlCommunity;

    @Lob
    private String content;
    private String uid;

}
