package com.hanium.catsby.cat.model;

import com.hanium.catsby.user.domain.Users;
import lombok.Builder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.awt.*;
import java.sql.Blob;
import java.text.SimpleDateFormat;

public class CatProfile {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cat_id;
    private String user_add;
    private long user_id;
    private String name;
    private String health;
    private String address;
    private int gender;
    private String image;
    private String content;
    private int spayed;
    private SimpleDateFormat created_time;
    private SimpleDateFormat updated_time;

    public CatProfile() {
    }

    //0811
    @Builder
    public CatProfile(long user_id, String user_add, String name, String health, String address,
                      int gender, String image, String content, int spayed) {
        this.user_id = user_id;
        this.user_add = user_add;
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

    public String getUser_add() {
        return user_add;
    }

    public void setUser_add(String user_add) {
        this.user_add = user_add;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSpayed() {
        return spayed;
    }

    public void setSpayed(int spayed) {
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
