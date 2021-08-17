package org.techtown.catsby.login.data.model;

public class UserRegister {

    private String uid;
    private String email;
    private String fcmToken;

    public UserRegister(String uid, String email, String fcmToken) {
        this.uid = uid;
        this.email = email;
        this.fcmToken = fcmToken;
    }

}
