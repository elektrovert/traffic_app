package com.eagerforlife.traffic.service;

import java.time.LocalDateTime;

public class TrafficMessage {

    private final LocalDateTime createdDate;
    private final String title;
    private final String description;
    private final float latitude;
    private final float longitude;
    private final String subcategory;
    private final int priority;

    public TrafficMessage(LocalDateTime createdDate, String title, String description,
                          float latitude, float longitude, String subcategory, int priority){
        this.createdDate = createdDate;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subcategory = subcategory;
        this.priority = priority;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String toString(){
        return new StringBuilder("Title:\n")
                .append(title)
                .append("Description:\n")
                .append(description)
                .append("Category:\n ")
                .append(subcategory)
                .append("Priority:\n")
                .append(priority)
                .toString();
    }
}
