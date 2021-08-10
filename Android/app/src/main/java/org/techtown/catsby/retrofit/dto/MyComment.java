package org.techtown.catsby.retrofit.dto;

public class MyComment {
    private int myComment_id;
    private BowlComment bowlComment;
    private TownComment townComment;

    public int getMyComment_id() {
        return myComment_id;
    }

    public BowlComment getBowlComment() {
        return bowlComment;
    }

    public TownComment getTownComment() {
        return townComment;
    }
}
