package com.hanium.catsby.bowl.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Bowl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_id")
    private Long id;

    @Column(unique = true)
    private String info;
    private String name;

    //주소 형식 찾아보기
    private String address;

    @Lob
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "bowl", cascade = CascadeType.ALL)
    private List<BowlCommunity> bowlCommunities = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bowl", cascade = CascadeType.ALL)
    private List<BowlUser> bowlUsers = new ArrayList<>();

}
