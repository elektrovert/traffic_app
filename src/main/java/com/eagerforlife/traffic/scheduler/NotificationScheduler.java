package com.eagerforlife.traffic.scheduler;

import com.eagerforlife.traffic.Utility.ClientIdValidator;
import com.eagerforlife.traffic.client.traffic.TrafficMessageClient;
import com.eagerforlife.traffic.repository.ClientRepository;
import com.eagerforlife.traffic.repository.RegisteredClient;
import com.eagerforlife.traffic.service.NotificationService;
import com.eagerforlife.traffic.service.TrafficMessage;
import com.eagerforlife.traffic.service.TrafficMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class NotificationScheduler {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ClientRepository clientRepository;
    private final NotificationService notificationService;
    private final TrafficMessageClient trafficMessageClient;

    @Autowired
    public NotificationScheduler(ClientRepository clientRepository,
                                 NotificationService notificationService,
                                 TrafficMessageClient trafficMessageClient) {
        this.clientRepository = clientRepository;
        this.notificationService = notificationService;
        this.trafficMessageClient = trafficMessageClient;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(4);

        scheduledExecutorService.scheduleAtFixedRate(new ScheduledNotificationTask(), 1, 10, MINUTES);
    }

    private class ScheduledNotificationTask implements Runnable {

        @Override
        public void run() {
            TrafficMessages trafficMessages = trafficMessageClient.getNotifications();
            Map<String, RegisteredClient> clientMap = clientRepository.getClientMap();
            sendNotifications(clientMap, trafficMessages.getMessageList());
        }

        private void sendNotifications(Map<String, RegisteredClient> clientMap, List<TrafficMessage> trafficMessages) {
            for (RegisteredClient client : clientMap.values()) {
                trafficMessages.forEach(msg -> {
                    if (isTrafficEventInRangeOfClient(client, msg)) {
                        if (client.getNotificationType().equals(ClientIdValidator.EMAIL)) {
                            notificationService.sendEmailNotification(client.getClientId(), msg);
                        } else {
                            notificationService.sendSmsNotification(client.getClientId(), msg);
                        }
                    }
                });
            }
        }

        private boolean isTrafficEventInRangeOfClient(RegisteredClient client, TrafficMessage msg) {
            // distance within 6km (rounded)
            final double distance = 0.05;
            final double clientLat = client.getClientPosition().getLatitude();
            final double clientLong = client.getClientPosition().getLongitude();
            final double msgLat = msg.getLatitude();
            final double msgLong = msg.getLongitude();

            return Math.sqrt(msgLat - clientLat) + Math.sqrt(msgLong - clientLong) <= Math.sqrt(distance);
        }
    }
}
