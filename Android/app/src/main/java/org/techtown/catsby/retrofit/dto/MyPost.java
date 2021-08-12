package org.techtown.catsby.retrofit.dto;

public class MyPost {
    private int myPost_id;
    private BowlCommunity bowlCommunity;
    private TownCommunity townCommunity;

    public int getMyPost_id() {
        return myPost_id;
    }

    public BowlCommunity getBowlCommunity() {
        return bowlCommunity;
    }

    public TownCommunity getTownCommunity() {
        return townCommunity;
    }
}
