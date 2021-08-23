package org.techtown.catsby.setting.data.model;

import org.techtown.catsby.community.data.model.TownCommunity;
import org.techtown.catsby.retrofit.dto.BowlCommunity;

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
