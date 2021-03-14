package com.eagerforlife.traffic.controller.registration;

import com.eagerforlife.traffic.repository.ClientPosition;

public class RegisterPositionRequest {

    private String id;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId(){
        return id;
    }

    public ClientPosition toClientPosition(){
        return new ClientPosition(latitude, longitude);
    }
}

