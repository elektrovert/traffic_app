package com.eagerforlife.traffic.service;

import com.eagerforlife.traffic.client.TrafficMessageClient;
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

    public String getTrafficNotifications(String id){
      return trafficMessageClient.getNotifications();
    }

    public void registerClient(String registrationId, ClientPosition clientPosition){
        clientRepository.registerClient(registrationId, clientPosition);
    }


    public void updatePosition(String id, ClientPosition clientPosition) {
        clientRepository.updateClientPosition(id, clientPosition);
    }

    public boolean isRegistered(String id) {
        if(clientRepository.getClient(id) != null) {
            return true;
        }
        return false;

    }
}
