package com.hanium.catsby.domain.bowl.dto;
import lombok.Data;

@Data
public class BowlDto {
    private Long id;
    private String name;
    private String address;
    private String image;
    private Double latitude;
    private Double longitude;
}
