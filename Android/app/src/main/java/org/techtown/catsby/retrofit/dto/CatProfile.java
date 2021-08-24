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
    private Boolean gender;
    private Blob image;
    private String content;
    private Boolean spayed;
    private SimpleDateFormat created_time;
    private SimpleDateFormat updated_time;
    private List<ClipData.Item> profile;

    public int getCatId(){
        return cat_id;
    }

    public String getCatName() {return name; }

    public String getHealth() {return health; }

    public String getAddress() {return address; }

    public Boolean getGender() {return gender; }

    public Blob getImage() {return image; }

    public Boolean getSpayed() {return spayed;}

    public String getContent() {return content; }

    public SimpleDateFormat getCreated_time() {return created_time; }

    public SimpleDateFormat getUpdated_time() {return updated_time; }

    public CatProfile(String name, String health, String address, Boolean gender, Blob image, String content, Boolean spayed) {
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
