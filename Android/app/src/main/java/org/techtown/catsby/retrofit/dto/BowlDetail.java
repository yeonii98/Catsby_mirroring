package org.techtown.catsby.retrofit.dto;

import java.util.List;

public class BowlDetail {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private List<BowlFeed> feed;
    private String image;
    private String address;

    public BowlDetail(Long id, String name, Double latitude, Double longitude, List<BowlFeed> feed, String image, String address) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.feed = feed;
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

    public List<BowlFeed> getFeed() {
        return feed;
    }

    public void setFeed(List<BowlFeed> feed) {
        this.feed = feed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
