package org.techtown.catsby.setting.data.model;

import org.techtown.catsby.community.data.model.TownComment;
import org.techtown.catsby.retrofit.dto.BowlComment;

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
