package com.hanium.catsby.bowl.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //주소 형식 찾아보기
    private String address;

    @Lob
    private byte[] image;

    @Column(name = "created_time")
    private LocalDateTime createDate;

    @Column(name = "updated_time")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "bowl", cascade = CascadeType.ALL)
    private List<BowlCommunity> bowlCommunities = new ArrayList<>();

<<<<<<< HEAD:Server/src/main/java/com/hanium/catsby/Bowl/domain/Bowl.java
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }
=======
    @JsonIgnore
    @OneToMany(mappedBy = "bowl", cascade = CascadeType.ALL)
    private List<BowlUser> bowlUsers = new ArrayList<>();

>>>>>>> 6b613cfe5b4d0d04ff5306cc18ff66a7f14abebf:Server/src/main/java/com/hanium/catsby/bowl/domain/Bowl.java
}
