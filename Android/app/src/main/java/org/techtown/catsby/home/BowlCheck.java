package org.techtown.catsby.home;

import android.graphics.drawable.Drawable;

public class BowlCheck {
    private Drawable icon ;
    private String text ;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }

    public Drawable getIcon() {
        return this.icon ;
    }

    public String getText() {
        return this.text ;
    }
}
