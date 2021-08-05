package com.hanium.catsby.BowlCommunity.domain;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "Bowl_Comment")
public class BowlComment {

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

    @Column(name = "created_time")
    private LocalDateTime createDate;

    @Column(name = "updated_time")
    private LocalDateTime updateDate;

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

    public void setCreateDate(){
        this.createDate = LocalDateTime.now();
    }

    public void setUpdateDate(){
        this.updateDate = LocalDateTime.now();
    }
}
