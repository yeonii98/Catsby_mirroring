package org.techtown.catsby.retrofit.dto;

public class BowlInfo {
    private Long id;
    private String name;
    private String address;
    private String image;
    private Double latitude;
    private Double longitude;

    public BowlInfo(Long id, String name, String address, String image, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
