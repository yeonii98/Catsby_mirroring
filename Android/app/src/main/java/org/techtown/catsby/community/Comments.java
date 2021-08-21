package org.techtown.catsby.community;

public class Comments {
    private String content;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comments(){

    }

    public Comments(String content){
        this.content = content;
    }
}
