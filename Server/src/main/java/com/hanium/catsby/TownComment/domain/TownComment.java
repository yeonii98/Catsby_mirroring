package com.hanium.catsby.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder
@Entity
@Table(name = "Town_Comment")
public class TownComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int townComment_id;

    @ManyToOne	(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//user_id라는 컬럼이 만들어짐
    private User user;

    @ManyToOne	(fetch = FetchType.LAZY)

    @JoinColumn(name = "town_community_id")//town_community_id라는 컬럼이 만들어짐

    private com.hanium.catsby.domain.TownCommunity town_community;


    @JoinColumn(name = "townCommunity_id")//town_community_id라는 컬럼이 만들어짐
    private com.hanium.catsby.domain.TownCommunity townCommunity;

    private TownCommunity town_community;

    private TownCommunity townCommunity;


    @JoinColumn(name = "townCommunity_id")//town_community_id라는 컬럼이 만들어짐
    private TownCommunity townCommunity;

    private String content;

    //    @CreationTimestamp//insert시 시간 자동 저장
    private String date;

    private String created_time;

    private String updated_time;

}
