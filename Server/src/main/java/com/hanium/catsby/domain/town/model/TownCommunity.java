package com.hanium.catsby.domain.town.model;

import com.hanium.catsby.domain.common.model.BaseTimeEntity;
import com.hanium.catsby.domain.user.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder
@Entity
@Table(name = "Town_Community")
public class TownCommunity extends BaseTimeEntity {

    @Id //PK지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "townCommunity_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")//use_id라는 컬럼이 만들어짐
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Users user;

    private String image;

    private String content;

    private String title;

    //    @CreationTimestamp//insert시 시간 자동 저장
    private String date;

    // OneToMany의 fetchType default값 -> LAZY
    @OneToMany(mappedBy = "townCommunity",fetch = FetchType.EAGER)//연관관계의 주인이 아니다.
    @JsonIgnoreProperties({"townCommunity"}) //무한참조 방지
    private List<TownComment> townComment;

    @OneToMany(mappedBy = "townCommunity")
    @JsonIgnoreProperties({"townCommunity"}) //무한참조 방지
    private List<TownLike> townLike;

    private boolean anonymous;
}
