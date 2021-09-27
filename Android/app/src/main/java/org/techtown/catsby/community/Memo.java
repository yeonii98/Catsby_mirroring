package org.techtown.catsby.community;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Memo {

    private int id;
    private String uid;
    private String maintext; //제목
    private String subtext; //내용
    private String nickname; // 닉네임
    private String date; // 날짜
    private int push;
    private Bitmap img;
    private int likeCnt = 0; //좋아요 개수
    private Bitmap userImg;

    public Memo(){

    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }

    public Memo(int id){
        this.id = id;
    }

    public Memo(int id, String uid, String maintext, String subtext, int likeCnt, String nickname, String date, Bitmap img, int push, Bitmap userImg) {
        this.id = id;
        this.uid = uid;
        this.maintext = maintext;
        this.subtext = subtext;
        this.likeCnt = likeCnt;
        this.nickname = nickname;
        this.date = date;
        this.img = img;
        this.push = push;
        this.userImg = userImg;
    }

    public Memo(int id, String uid, String maintext, String subtext, String nickname, String date, Bitmap img, Bitmap userBm) {
        this.id = id;
        this.uid = uid;
        this.maintext = maintext;
        this.subtext = subtext;
        this.nickname = nickname;
        this.date = date;
        this.img = img;
        this.userImg = userBm;
    }

    public Memo(int id, String uid, String maintext, String subtext, String nickname, String date, Bitmap img, int likeCnt, int push, Bitmap userImg) {
        this.id = id;
        this.uid = uid;
        this.maintext = maintext;
        this.subtext = subtext;
        this.nickname = nickname;
        this.date = date;
        this.img = img;
        this.push = push;
        this.likeCnt = likeCnt;
        this.userImg = userImg;
    }

    public Memo(String uid, String maintext, String subtext, String nickname, String date,Bitmap bm) {
        this.uid = uid;
        this.maintext = maintext;
        this.subtext = subtext;
        this.nickname = nickname;
        this.date = date;
        this.img = bm;
    }

    public Memo(String title, String content) {
        this.maintext = title;
        this.subtext = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        this.subtext = subtext;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}

