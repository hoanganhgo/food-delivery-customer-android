package com.hcmus.fit.customer_apps.models;

import com.hcmus.fit.customer_apps.utils.AppUtil;

import java.util.Calendar;

public class ReviewModel {
    private String avatarUrl;
    private String userName;
    private int stars;
    private String content;
    private Calendar createAt;

    public ReviewModel() {
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getCreateAt() {
        return createAt;
    }

    public String getDateTimeCreate() {
        return AppUtil.getTimeString(createAt) + " " + AppUtil.getDateString(createAt);
    }

    public void setCreateAt(Calendar createAt) {
        this.createAt = createAt;
    }
}
