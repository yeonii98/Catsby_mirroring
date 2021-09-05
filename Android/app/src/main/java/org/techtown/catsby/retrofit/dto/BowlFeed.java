package org.techtown.catsby.retrofit.dto;

public class BowlFeed {

    String bowlName;
    String bowlAddress;;
    String formatTime;
    String time;

    public String getBowlName() {
        return bowlName;
    }

    public void setBowlName(String bowlName) {
        this.bowlName = bowlName;
    }

    public String getBowlAddress() {
        return bowlAddress;
    }

    public void setBowlAddress(String bowlAddress) {
        this.bowlAddress = bowlAddress;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BowlFeed(String bowlName, String bowlAddress, String formatTime, String time) {
        this.bowlName = bowlName;
        this.bowlAddress = bowlAddress;
        this.formatTime = formatTime;
        this.time = time;
    }
}
