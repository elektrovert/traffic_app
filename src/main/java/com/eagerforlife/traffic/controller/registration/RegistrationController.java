package com.eagerforlife.traffic.controller.registration;

import com.eagerforlife.traffic.Utility.ClientIdValidator;
import com.eagerforlife.traffic.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

@RestController()
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ClientIdValidator clientIdValidator;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.clientIdValidator = new ClientIdValidator();
    }

    @PostMapping("/traffic/notifications/register")
    public void registerForNotifications(@RequestBody RegisterClientRequest registerClientRequest) {
        if (registerClientRequest == null) {
            throw new InvalidParameterException("Invalid RegisterClientRequest body");
        }

        final String id = registerClientRequest.getId();
        String notificationType = validateId(id);
        registrationService.registerClient(id, registerClientRequest.toClientPosition(), notificationType);
    }

    @DeleteMapping("/traffic/notifications/register")
    public void deleteNotificationRegistration(@RequestBody DeleteRegistrationRequest deleteRegistrationRequest) {
        if (deleteRegistrationRequest == null) {
            throw new InvalidParameterException("Invalid DeleteRegistrationRequest body");
        }

        final String id = deleteRegistrationRequest.getId();
        validateId(id);
        registrationService.deleteRegistration(id);

    }

    /**
     * Validates whether or not the ID is in an acceptable email/phone number format
     *
     * @param id The ID to be validated
     * @return "email" or "phone"
     */
    private String validateId(String id) {
        if (id != null) {
            if (clientIdValidator.validateEmailFormat(id)) {
                return ClientIdValidator.EMAIL;
            }

            if (clientIdValidator.validatePhoneNumberFormat(id)) {
                return ClientIdValidator.PHONE;
            }
        }
        throw new InvalidParameterException("ID must be a valid email address or phone number");
    }
}
