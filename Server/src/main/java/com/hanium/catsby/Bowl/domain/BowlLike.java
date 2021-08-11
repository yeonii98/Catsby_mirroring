package com.hanium.catsby.Bowl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.User.domain.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "Bowl_Like")
public class BowlLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowlLike_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bowlCommunity_id")
    private BowlCommunity bowlCommunity;

    @Column(name = "created_time")
    private LocalDateTime createDate;

    public void setBowlCommunity(BowlCommunity bowlCommunity){
        this.bowlCommunity = bowlCommunity;
    }

    public void setCreateDate(){
        this.createDate = LocalDateTime.now();
    }

    public void setUser(Users user){
        this.user = user;
    }
}
