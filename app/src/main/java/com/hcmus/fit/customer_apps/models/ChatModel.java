package com.hcmus.fit.customer_apps.models;

public class ChatModel {
    private boolean myself;
    private String content;

    public ChatModel() {
    }

    public boolean isMyself() {
        return myself;
    }

    public void setMyself(boolean myself) {
        this.myself = myself;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
