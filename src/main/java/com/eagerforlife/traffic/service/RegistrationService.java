package com.eagerforlife.traffic.service;

import com.eagerforlife.traffic.client.traffic.TrafficMessageClient;
import com.eagerforlife.traffic.repository.ClientPosition;
import com.eagerforlife.traffic.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final TrafficMessageClient trafficMessageClient;
    private final ClientRepository clientRepository;

    @Autowired
    public RegistrationService(TrafficMessageClient trafficMessageClient, ClientRepository clientRepository){
        this.trafficMessageClient = trafficMessageClient;
        this.clientRepository = clientRepository;
    }

    public void registerClient(String registrationId, ClientPosition clientPosition, String notificationType){
        clientRepository.registerClient(registrationId, clientPosition, notificationType);
    }

    public boolean isRegistered(String id) {
        if(clientRepository.getClient(id) != null) {
            return true;
        }
        return false;
    }

    public void deleteRegistration(String id) {
        clientRepository.deleteClient(id);
    }
}
