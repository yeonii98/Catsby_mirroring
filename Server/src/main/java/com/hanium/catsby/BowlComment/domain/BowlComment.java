package com.hanium.catsby.BowlComment.domain;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BowlComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_community_id")
    private BowlCommunity bowlCommunity;

    private LocalDateTime commentDate;

    @Lob
    private String content;

    /*
    public void setBowlCommunity(BowlCommunity bowlCommunity) {
        this.bowlCommunity = bowlCommunity;
        bowlCommunity.getBowlComments().add(this);
    }*/
}
