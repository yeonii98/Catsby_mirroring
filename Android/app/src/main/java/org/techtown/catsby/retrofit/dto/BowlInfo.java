package org.techtown.catsby.retrofit.dto;

public class BowlInfo {
    private Long id;
    private String name;
    private String address;
    private String image;

    public BowlInfo(Long id, String name,String address,String image) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
