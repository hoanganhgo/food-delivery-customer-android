package com.hcmus.fit.customer_apps.models;

public class Banner {
    private String image = "";
    private String link = "";

    public Banner(String image, String link) {
        this.image = image;
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }
}
