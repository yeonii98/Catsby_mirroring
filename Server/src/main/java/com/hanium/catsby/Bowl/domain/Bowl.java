package com.hanium.catsby.Bowl.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Bowl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bowl_id")
    private Long id;

    @Lob
    private String info;
    private String name;

    //찾아보기
    private String address;

    private LocalDateTime enrollDate;

    @Lob
    private byte[] image;

    @JsonIgnore
    @OneToMany(mappedBy = "bowl", cascade = CascadeType.ALL)
    private List<BowlCommunity> bowlCommunities = new ArrayList<>();

}
