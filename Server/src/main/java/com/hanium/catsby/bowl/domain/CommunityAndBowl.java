package com.hanium.catsby.bowl.domain;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "Community_Bowl")
public class CommunityAndBowl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_bowl_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowl_id")
    private Bowl bowl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;
}
