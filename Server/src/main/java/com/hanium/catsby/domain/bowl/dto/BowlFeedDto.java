package com.hanium.catsby.domain.bowl.dto;

import com.hanium.catsby.domain.bowl.model.BowlFeed;
import com.hanium.catsby.util.NotificationUtil;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class BowlFeedDto {

    String bowlName;
    String bowlAddress;
    String formatTime;
    String time;

    public BowlFeedDto(BowlFeed bf) {
        this.bowlName = bf.getBowl().getName();
        this.bowlAddress = bf.getBowl().getAddress();
        this.formatTime = NotificationUtil.getDateDifference(bf.getCreatedDate());
        this.time = bf.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
