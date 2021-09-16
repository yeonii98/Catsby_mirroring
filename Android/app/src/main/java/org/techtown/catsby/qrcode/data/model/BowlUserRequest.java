package org.techtown.catsby.qrcode.data.model;

public class BowlUserRequest {

    private String bowlInfo;
    private double latitude;
    private double longitude;

    public BowlUserRequest(String bowlInfo, double latitude, double longitude) {
        this.bowlInfo = bowlInfo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBowlInfo() {
        return bowlInfo;
    }

    public void setBowlInfo(String bowlInfo) {
        this.bowlInfo = bowlInfo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}