package org.techtown.catsby.retrofit.dto;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String address;
    private String createDate;
    private String updateDate;


    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
