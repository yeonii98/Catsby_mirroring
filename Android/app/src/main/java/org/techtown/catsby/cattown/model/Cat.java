package org.techtown.catsby.cattown.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Cat {
    private Integer catpicture;
    private String name;
    private int helppeople;
    private int isdone;

    public Cat(String name, Integer catpicture, int isdone) {
        this.name = name;
        this.catpicture = catpicture;
        //this.helpPeople = helpPeople;
        this.isdone = isdone;
    }

    public Integer getCatPicture() {
        return catpicture;
    }
    public void setCatPicture(Integer catpicture) {
        this.catpicture = catpicture;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getHelpPeople() {
        return helppeople;
    }
    public void setHelpPeople(int helppeople) {
        this.helppeople=helppeople;
    }


}


