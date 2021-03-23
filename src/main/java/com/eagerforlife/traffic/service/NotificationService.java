package com.eagerforlife.traffic.service;

import com.eagerforlife.traffic.client.email.SendGridClient;
import com.eagerforlife.traffic.client.sms.TwilioClient;
import com.eagerforlife.traffic.repository.ClientPosition;
import com.eagerforlife.traffic.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final ClientRepository clientRepository;
    private final SendGridClient sendGridClient;
    private final TwilioClient twilioClient;

    @Autowired
    public NotificationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.sendGridClient = new SendGridClient();
        this.twilioClient = new TwilioClient();
    }

    public void sendEmailNotification(String email, TrafficMessage msg) {
        sendGridClient.sendEmail(email, msg.toString());
    }

    public void sendSmsNotification(String phoneNumber, TrafficMessage msg) {
        twilioClient.sendSMS(phoneNumber, msg.toString());
    }

    public void updatePosition(String id, ClientPosition clientPosition) {
        clientRepository.updateClientPosition(id, clientPosition);
    }
}
