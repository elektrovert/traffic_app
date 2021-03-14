package com.eagerforlife.traffic.repository;

import com.eagerforlife.traffic.scheduler.DeregisterScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

@Repository
public class ClientRepository {

    private final Map<String, RegisteredClient> registeredClients;
    private DeregisterScheduler deregisterScheduler;

    @Autowired
    public ClientRepository(DeregisterScheduler deregisterScheduler){
        this.deregisterScheduler = deregisterScheduler;
        this.registeredClients = new HashMap<>();
    }

    public ClientRepository() {
        registeredClients = new HashMap<>();
    }

    public void registerClient(String id, ClientPosition position) {
        registeredClients.put(id, new RegisteredClient(id, position, now(UTC)));
        deregisterScheduler.scheduleClientDeregister(id, registeredClients);
    }

    public void updateClientPosition(String id, ClientPosition position) {
        registeredClients.get(id).setClientPosition(position);
    }

    public RegisteredClient getClient(String id) {
        return registeredClients.get(id);
    }
}
