package org.techtown.catsby.retrofit.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BowlList {
    @SerializedName("data")
    private List<BowlDto> bowls;

    public int size() {
        return bowls.size();
    }

    public List<BowlDto> getBowls() {
        return bowls;
    }
}
