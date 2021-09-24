package org.techtown.catsby.retrofit.dto;

import android.content.ClipData;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.List;


public class CatProfile {

    private int cat_id;
    private String name;
    private String health;
    private String address;
    private int gender;
    private String image;
    private String content;
    private int spayed;
    private SimpleDateFormat created_time;
    private SimpleDateFormat updated_time;
    private List<ClipData.Item> profile;
    private long userid;
    private User user;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCatId(){
        return cat_id;
    }

    public String getCatName() {return name; }

    public String getHealth() {return health; }

    public String getAddress() {return address; }

    public int getGender() {return gender; }

    public String getImage() {return image; }

    public int getSpayed() {return spayed;}

    public String getContent() {return content; }

    public SimpleDateFormat getCreated_time() {return created_time; }

    public SimpleDateFormat getUpdated_time() {return updated_time; }

    public CatProfile(long userid, String name, String health, String address, int gender, String image, String content, int spayed) {
        this.userid = userid;
        this.name = name;
        this.health = health;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.content = content;
        this.spayed = spayed;
    }

    //public List<ClipData.Item> getCatProfile(){
        //return profile;
    //}






}
