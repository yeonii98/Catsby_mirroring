package org.techtown.catsby.retrofit.dto;

import java.time.LocalDateTime;

public class BowlDto {
    private int id;
    private String name;
    private String info;
    private String address;
    private byte[] image;
    private LocalDateTime created_time;
    private LocalDateTime updated_time;

    public BowlDto( String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getBowl_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getAddress() {
        return address;
    }

    public byte[] getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreated_time() {
        return created_time;
    }

    public LocalDateTime getUpdated_time() {
        return updated_time;
    }
}
