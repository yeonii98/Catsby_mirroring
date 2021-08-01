package com.hanium.catsby.BowlCommunity.domain;

import com.hanium.catsby.Bowl.domain.Bowl;
import com.hanium.catsby.BowlComment.domain.BowlComment;
import com.hanium.catsby.BowlLike.domain.BowlLike;
import com.hanium.catsby.User.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class BowlCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_community_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_id")
    private Bowl bowl;

    @Lob
    private byte[] image;

    @Lob
    private String content;

    private LocalDateTime uploadDate;

    @OneToMany(mappedBy = "bowlCommunity", cascade = CascadeType.ALL)
    private List<BowlComment> bowlComments = new ArrayList<>();

    public void setBowl(Bowl bowl) {
        this.bowl = bowl;
        bowl.getBowlCommunities().add(this);
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bowl_like_id")
    private BowlLike bowlLike;

    public void setBowlLike(BowlLike bowlLike) {
        this.bowlLike = bowlLike;
        bowlLike.setBowlCommunity(this);
    }


}
