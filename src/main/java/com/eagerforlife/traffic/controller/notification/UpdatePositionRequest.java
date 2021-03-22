package com.eagerforlife.traffic.controller.notification;

import com.eagerforlife.traffic.repository.ClientPosition;

public class UpdatePositionRequest {
    /**
     * The Resistered ID of the client
     * Must be a valid email address or phone number
     */
    private String id;
    /**
     * The current latitude of the client
     */
    private double latitude;
    /**
     * The current longitude of the client
     */
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

    public ClientPosition toClientPosition() {
        return new ClientPosition(latitude, longitude);
    }
}

