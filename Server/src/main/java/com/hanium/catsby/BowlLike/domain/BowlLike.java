package com.hanium.catsby.BowlLike.domain;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
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

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    //private User user;

    @OneToOne(mappedBy = "bowlLike", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BowlCommunity bowlCommunity;


}
