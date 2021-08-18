package org.techtown.catsby.login.data.model;

public class LoginResponse {
    private String customToken;
    private boolean saved;

    public String getCustomToken() {
        return customToken;
    }

    public void setCustomToken(String customToken) {
        this.customToken = customToken;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
