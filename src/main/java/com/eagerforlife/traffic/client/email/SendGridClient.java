package com.eagerforlife.traffic.client.email;

import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Slf4j
public class SendGridClient {
    private final Logger logger = LoggerFactory.getLogger(SendGridClient.class);

    // API keys and passwords would be stored in a secure manner and accessed via properties files or environment
    // variables (by a dedicated app user) or similar
    private final String SendgridApiKey = "api_key";
    private final Email from = new Email("paul.apps@eagerforlife.com");
    private final String trafficNotification = "Traffic Notification!";

    public void sendEmail(String toAddress, String notification) {
        Email to = new Email(toAddress);
        Content content = new Content("Text/Plain", notification);
        Mail mail = new Mail(from, trafficNotification, to, content);

        SendGrid sendGrid = new SendGrid(SendgridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            logger.info("to: {}; response: {}", to, response.getStatusCode());

        } catch (IOException e) {
            logger.error(e.getMessage());
            /*
             * If we get here we might want to try resending the message a couple of times
             * bases on predetermined rules.
             */
        }
    }

}
