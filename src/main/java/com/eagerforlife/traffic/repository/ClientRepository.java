package com.eagerforlife.traffic.repository;

import com.eagerforlife.traffic.scheduler.DeregisterScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

@Repository
public class ClientRepository {

    private final Map<String, RegisteredClient> registeredClients;
    private DeregisterScheduler deregisterScheduler;

    @Autowired
    public ClientRepository(DeregisterScheduler deregisterScheduler){
        this.deregisterScheduler = deregisterScheduler;
        this.registeredClients = new ConcurrentHashMap<>();
    }

    public ClientRepository() {
        registeredClients = new ConcurrentHashMap<>();
    }

    public void registerClient(String id, ClientPosition position, String  notificationType) {
        registeredClients.put(id, new RegisteredClient(id, position, now(UTC), notificationType));
        deregisterScheduler.scheduleClientDeregister(id, registeredClients);
    }

    public void updateClientPosition(String id, ClientPosition position) {
        registeredClients.get(id).setClientPosition(position);
    }

    public RegisteredClient getClient(String id) {
        return registeredClients.get(id);
    }

    public void deleteClient(String id) {
        registeredClients.remove(id);
    }

    public Map<String, RegisteredClient> getClientMap() {
        return registeredClients;
    }
}
