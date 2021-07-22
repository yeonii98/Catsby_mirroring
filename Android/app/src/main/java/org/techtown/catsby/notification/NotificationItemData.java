package org.techtown.catsby.notification;

public class NotificationItemData {
    int image;
    String title;
    String content;

    public NotificationItemData(int image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
