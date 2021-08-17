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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;

    @Lob
    private String content;

    public void setBowlCommunity(BowlCommunity bowlCommunity) {
        this.bowlCommunity = bowlCommunity;
        bowlCommunity.getBowlComments().add(this);
    }

    public void setUser(Users user){
        this.user = user;
        user.getBowlComments().add(this);
    }

}
