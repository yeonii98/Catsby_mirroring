package com.hanium.catsby.cat.model;

import lombok.Builder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.awt.*;
import java.sql.Blob;
import java.text.SimpleDateFormat;

public class CatProfile {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //0811
    @Builder
    public CatProfile(String name, String health, String address,
                      Boolean gender, Blob image, String content, Boolean spayed) {
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
