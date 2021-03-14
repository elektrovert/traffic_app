package com.eagerforlife.traffic.repository;

import org.joda.time.DateTime;

public class RegisteredClient {

    private final String clientId;
    private final DateTime registrationTime;
    private ClientPosition clientPosition;

    public RegisteredClient(String clientId, ClientPosition clientPosition, DateTime registrationTime) {
        this.clientId = clientId;
        this.registrationTime = registrationTime;
        this.clientPosition = clientPosition;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientPosition getClientPosition() {
        return clientPosition;
    }

    public DateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setClientPosition(ClientPosition clientPosition) {
        this.clientPosition = clientPosition;
    }
}
