package com.eagerforlife.traffic.controller.notification;

import com.eagerforlife.traffic.repository.ClientPosition;

public class UpdatePositionRequest {

    private String id;
    private double latitude;
    private double longitude;

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public ClientPosition toClientPosition(){
        return new ClientPosition(latitude, longitude);
    }
}

