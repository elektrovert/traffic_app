package com.eagerforlife.traffic.scheduler;

import com.eagerforlife.traffic.repository.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.HOURS;

@Service
public class DeregisterScheduler {

    private final ScheduledExecutorService scheduledExecutorService;

    public DeregisterScheduler() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(4);

    }

    public void scheduleClientDeregister(String id, Map<String, RegisteredClient> registeredClientMap) {
        scheduledExecutorService.schedule(new ScheduledDeregisterTask(id, registeredClientMap), 24, HOURS);
    }

    private class ScheduledDeregisterTask implements Runnable {

        private final String id;
        private final Map<String, RegisteredClient> registeredClientMap;

        public ScheduledDeregisterTask(String id, Map<String, RegisteredClient> registeredClientMap) {
            this.id = id;
            this.registeredClientMap = registeredClientMap;
        }

        @Override
        public void run() {
            registeredClientMap.remove(id);
        }
    }
}
