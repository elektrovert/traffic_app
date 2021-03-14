package com.eagerforlife.traffic.repository;

public class ClientPosition {
    private double latitude;
    private double longitude;

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
