package org.techtown.catsby.cattown.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

import org.techtown.catsby.retrofit.dto.User;

public class Cat {
    private Bitmap catpicture;
    private String cat_id;
    private String name;
    private String catloc;
    private String catgen;
    private int helppeople;
    private int isdone;
    private long user_id;
    private User user;


    public Cat(long user_id, String name, Bitmap catpicture, String cat_id, String catloc, String catgen, int isdone) {
        this.user_id = user_id;
        this.name = name;
        this.catpicture = catpicture;
        this.cat_id = cat_id;
        this.catloc = catloc;
        this.catgen = catgen;
        this.isdone = isdone;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {

        this.cat_id = cat_id;
    }

    public Bitmap getCatPicture() {

        return catpicture;
    }

    public void setCatPicture(Bitmap catpicture) {

        this.catpicture = catpicture;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getCatloc() {
        return catloc;
    }

    public void setCatloc(String catloc) {
        this.catloc = catloc;
    }

    public String getCatgen() {
        return catgen;
    }

    public void setCatgen(String catgen) {
        this.catgen = catgen;
    }

    public int getHelpPeople() {

        return helppeople;
    }
    public void setHelpPeople(int helppeople) {

        this.helppeople=helppeople;
    }


}


