package com.eagerforlife.traffic.client.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TwilioClient {
    final Logger logger = LoggerFactory.getLogger(TwilioClient.class);

    // In a real application this information would be stored securely
    // and accessed by the applications dedicated user via environment variables
    private final String accountSid = "account_sid";
    private final String authToken = "auth_token";
    private final String ourPhoneNumber = "+12183767042";

    public void sendSMS(String phoneNumber, String smsMessage) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(ourPhoneNumber),
                smsMessage).create();
        logger.info("Sms sent to {}", phoneNumber);
    }

}
