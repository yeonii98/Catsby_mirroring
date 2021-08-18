package org.techtown.catsby.community;

import java.util.HashMap;
import java.util.Map;

public class Memo {
    private int id;
    private String maintext; //제목
    private String subtext; //내용
    private String nickname; // 닉네임
    private String date; // 날짜
    private int isdone; //완료여부
    private boolean push = true;
    private int likeCnt = 0; //좋아요 개수

    public Memo(){

    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public Memo(int id, String maintext, String subtext, String nickname, String date, int likeCnt ,int isdone) {
        this.id = id;
        this.maintext = maintext;
        this.subtext = subtext;
        this.nickname = nickname;
        this.date = date;
        this.likeCnt = likeCnt;
        this.isdone = isdone;
    }

    public Memo(String title, String content, int i) {
        this.maintext = title;
        this.subtext = content;
        this.isdone = i;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIsdone() {
        return isdone;
    }

    public void setIsdone(int isdone) {
        this.isdone = isdone;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

}

