package org.techtown.catsby.retrofit.dto;

import java.sql.Blob;
import java.text.SimpleDateFormat;

public class CatProfile {

    private Integer cat_id;
    private String name;
    private String health;
    private String address;
    private Boolean gender;
    private Blob image;
    private String content;
    private Boolean spayed;
    private SimpleDateFormat created_time;
    private SimpleDateFormat updated_time;




}
