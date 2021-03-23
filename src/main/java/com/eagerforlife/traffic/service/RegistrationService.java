package com.eagerforlife.traffic.service;

import com.eagerforlife.traffic.repository.ClientPosition;
import com.eagerforlife.traffic.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final ClientRepository clientRepository;

    @Autowired
    public RegistrationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void registerClient(String registrationId, ClientPosition clientPosition, String notificationType) {
        clientRepository.registerClient(registrationId, clientPosition, notificationType);
    }

    public boolean isRegistered(String id) {
        return clientRepository.getClient(id) != null;
    }

    public void deleteRegistration(String id) {
        clientRepository.deleteClient(id);
    }
}
