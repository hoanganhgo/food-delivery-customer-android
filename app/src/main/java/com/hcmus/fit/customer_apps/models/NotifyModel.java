package com.hcmus.fit.customer_apps.models;

public class NotifyModel {
    private String id;
    private String title;
    private String content;
    private String avatarUrl;

    public NotifyModel(String id, String title, String content, String avatarUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
