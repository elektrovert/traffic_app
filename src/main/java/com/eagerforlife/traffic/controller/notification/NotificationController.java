package com.eagerforlife.traffic.controller.notification;

import com.eagerforlife.traffic.Utility.ClientIdValidator;
import com.eagerforlife.traffic.service.NotificationService;
import com.eagerforlife.traffic.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
public class NotificationController {
    private final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final RegistrationService registrationService;
    private final NotificationService notificationService;
    private final ClientIdValidator clientIdValidator;

    @Autowired
    public NotificationController(RegistrationService registrationService, NotificationService notificationService) {
        this.registrationService = registrationService;
        this.notificationService = notificationService;
        this.clientIdValidator = new ClientIdValidator();
    }

    /**
     * Updates the current position of a registered client
     *
     * @param updatePositionRequest A position request object containing the registered ID of the client and the current position
     */
    @PutMapping("/traffic/notifications")
    public void updatePosition(@RequestBody UpdatePositionRequest updatePositionRequest) {
        notificationService.updatePosition(updatePositionRequest.getId(), updatePositionRequest.toClientPosition());
    }

    /**
     * Validates whether or not the ID is in an acceptable email/phone number format
     * Validates whether or not the client is registered
     *
     * @param id The client ID to be registered
     */
    private void validateId(String id) {
        if (id != null) {
            if (clientIdValidator.validateEmailFormat(id) || clientIdValidator.validatePhoneNumberFormat(id)) {
                if (!registrationService.isRegistered(id)) {
                    logger.warn("Client {} is not registered", id);
                    throw new InvalidParameterException("Client is not registered");
                }
                return;
            }
        }
        logger.warn("Invalid email or phone number: {}", id);
        throw new InvalidParameterException("ID must be a valid email address or phone number");

    }

}
