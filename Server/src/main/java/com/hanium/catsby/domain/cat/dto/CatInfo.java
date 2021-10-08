package com.hanium.catsby.domain.cat.dto;

import lombok.Data;

@Data
public class CatInfo {

    private String name;
    private String health;
    private String address;
    private int gender;
    private String content;
    private int spayed;
}
