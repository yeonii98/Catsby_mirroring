package org.techtown.catsby.retrofit.dto;

public class Bowl {
    private int id;
    private String name;
    private String info;
    private String address;
    private byte[] image;
    private String created_time;
    private String updated_time;

    public Bowl(String name, String address) {
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

    public String getCreated_time() {
        return created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }
}
