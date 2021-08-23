package com.hanium.catsby.Town.domain;

import com.hanium.catsby.util.BaseTimeEntity;
import com.hanium.catsby.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder
@Entity
@Table(name = "Town_Like")
public class TownLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "townLike_id")
    private int id;

    @ManyToOne	(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")//user_id라는 컬럼이 만들어짐
    private Users user;

    @ManyToOne	(fetch = FetchType.EAGER)
    @JoinColumn(name = "townCommunity_id")//town_community_id_Id라는 컬럼이 만들어짐
    private TownCommunity townCommunity;
}

