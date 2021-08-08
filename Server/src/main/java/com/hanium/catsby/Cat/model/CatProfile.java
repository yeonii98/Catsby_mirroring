package com.hanium.catsby.cat.model;

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

    public CatProfile(Integer cat_id, String name, String health, String address,
                      Boolean gender, Blob image, String content, Boolean spayed,
                      SimpleDateFormat created_time, SimpleDateFormat updated_time) {
        this.cat_id = cat_id;
        this.name = name;
        this.health = health;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.content = content;
        this.spayed = spayed;
        this.created_time = created_time;
        this.updated_time = updated_time;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSpayed() {
        return spayed;
    }

    public void setSpayed(Boolean spayed) {
        this.spayed = spayed;
    }

    public SimpleDateFormat getCreated_time() {
        return created_time;
    }

    public void setCreated_time(SimpleDateFormat created_time) {
        this.created_time = created_time;
    }

    public SimpleDateFormat getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(SimpleDateFormat updated_time) {
        this.updated_time = updated_time;
    }
}
