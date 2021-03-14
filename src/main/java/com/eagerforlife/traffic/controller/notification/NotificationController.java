package com.eagerforlife.traffic.controller.notification;

import com.eagerforlife.traffic.Utility.ClientIdValidator;
import com.eagerforlife.traffic.service.NotificationService;
import com.eagerforlife.traffic.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
public class NotificationController {

    private final RegistrationService registrationService;
    private final NotificationService notificationService;
    private final ClientIdValidator clientIdValidator;

    @Autowired
    public NotificationController(RegistrationService registrationService, NotificationService notificationService) {
        this.registrationService = registrationService;
        this.notificationService = notificationService;
        this.clientIdValidator = new ClientIdValidator();
    }

    @GetMapping("/traffic/notifications")
    @ResponseBody
    public GetNotificationsRequest getNotifications(@RequestBody GetNotificationsRequest notificationsRequest) {
        System.out.println("GET: Received ID: " + notificationsRequest.getId());
        validateId(notificationsRequest.getId());
        return notificationsRequest;
    }

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
        if (clientIdValidator.validateEmailFormat(id) || clientIdValidator.validatePhoneNumberFormat(id)) {
            if (!registrationService.isRegistered(id)) {
                throw new InvalidParameterException("Client is not registered");
            }
            return;
        }
        throw new InvalidParameterException("ID must be a valid email address or phone number");

    }

}
