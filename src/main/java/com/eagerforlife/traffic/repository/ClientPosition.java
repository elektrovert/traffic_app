package com.eagerforlife.traffic.repository;

public class ClientPosition {
    private final double latitude;
    private final double longitude;

    public ClientPosition(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
