package com.hanium.catsby.bowl.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class BowlDetailDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private List<BowlFeedDto> feed;
    private String image;
    private String address;
}
