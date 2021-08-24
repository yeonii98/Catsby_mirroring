package org.techtown.catsby.cattown.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Cat {
    private byte[] catpicture;
    private String cat_id;
    private String name;
    private int helppeople;
    private int isdone;

    public Cat(String name, byte[] catpicture, String cat_id, int isdone) {
        this.name = name;
        this.catpicture = catpicture;
        this.cat_id = cat_id;
        //this.helpPeople = helpPeople;
        this.isdone = isdone;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public byte[] getCatPicture() {
        return catpicture;
    }
    public void setCatPicture(byte[] catpicture) {
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


