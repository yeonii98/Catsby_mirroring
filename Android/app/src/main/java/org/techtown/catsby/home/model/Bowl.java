package org.techtown.catsby.home.model;

import java.time.LocalDateTime;

public class Bowl {
    private int id;
    private int image;
    private String name;
    private String info;
    private String address;
    private LocalDateTime localDateTime;

    public Bowl(int id, int image, String name, String info, String address, LocalDateTime localDateTime) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.info = info;
        this.address = address;
        this.localDateTime = localDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getImage() {
        return image;
    }

    public void setImage( int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
