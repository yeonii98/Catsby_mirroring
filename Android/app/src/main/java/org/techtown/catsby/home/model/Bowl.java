package org.techtown.catsby.home.model;

import java.time.LocalDateTime;

public class Bowl {
    private int image;
    private String name;
    private String info;
    private String address;
    private String localDateTime;

    public Bowl(int image, String name, String info, String address, String localDateTime) {
        this.image = image;
        this.name = name;
        this.info = info;
        this.address = address;
        this.localDateTime = localDateTime;
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

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
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
