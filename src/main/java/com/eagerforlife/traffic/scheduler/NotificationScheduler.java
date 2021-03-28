package com.eagerforlife.traffic.scheduler;

import com.eagerforlife.traffic.client.traffic.TrafficMessageClient;
import com.eagerforlife.traffic.repository.ClientRepository;
import com.eagerforlife.traffic.repository.RegisteredClient;
import com.eagerforlife.traffic.service.NotificationService;
import com.eagerforlife.traffic.service.TrafficMessage;
import com.eagerforlife.traffic.service.TrafficMessages;
import com.eagerforlife.traffic.utility.ClientIdValidator;
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
            /*
             * Took this calculation from here:
             * https://stackoverflow.com/a/16794680/571875
             *
             * Tweaked it to remove height calculation
             */
            final double clientLat = client.getClientPosition().getLatitude();
            final double clientLong = client.getClientPosition().getLongitude();
            final double msgLat = msg.getLatitude();
            final double msgLong = msg.getLongitude();

            final int R = 6371; // Radius of the Earth

            final double latDistance = Math.toRadians(msgLat - clientLat);
            final double lonDistance = Math.toRadians(msgLong - clientLong);
            final double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(clientLat)) * Math.cos(Math.toRadians(msgLat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c * 1000; // convert to metres

            return distance < 5000; // return true if notification is within 5km
        }
    }
}
