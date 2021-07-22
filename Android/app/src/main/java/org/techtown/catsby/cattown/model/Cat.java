package org.techtown.catsby.cattown.model;

import android.graphics.drawable.Drawable;

public class Cat {
    private int catpicture;
    private String name;
    private int helppeople;

    public int getCatPicture() {
        return catpicture;
    }
    public void setCatPicture(int catpicture) {
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


