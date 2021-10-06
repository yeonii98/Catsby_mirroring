package org.techtown.catsby.retrofit.dto;

public class CatInfo {

    private String name;
    private String health;
    private String address;
    private int gender;
    private String content;
    private int spayed;

    public CatInfo(String name, String health, String address, int gender, String content, int spayed) {
        this.name = name;
        this.health = health;
        this.address = address;
        this.gender = gender;
        this.content = content;
        this.spayed = spayed;
    }
}
