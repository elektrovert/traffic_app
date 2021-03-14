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

    private RegistrationService registrationService;
    private ClientIdValidator clientIdValidator;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.clientIdValidator = new ClientIdValidator();
    }


    @PostMapping("/traffic/notifications/register")
    public void registerForNotifications(@RequestBody RegisterPositionRequest registerPositionRequest) {
        System.out.println("POST: Received ID:" + registerPositionRequest);
        String id = registerPositionRequest.getId();
        validateId(id);
        registrationService.registerClient(id, registerPositionRequest.toClientPosition());
    }


    @DeleteMapping("/traffic/notifications/register")
    public void deleteNotificationRegistration(@RequestBody DeleteRegistrationRequest deleteRegistrationRequest) {
        validateId(deleteRegistrationRequest.getId());
        System.out.println("DELETE: Received ID:" + deleteRegistrationRequest.getId());
    }

    /**
     * Validates whether or not the ID is in an acceptable email/phone number format
     *
     * @param id The client ID to be registered
     */
    private void validateId(String id) {
        boolean ematch = clientIdValidator.validateEmailFormat(id);
        boolean pmatch = clientIdValidator.validatePhoneNumberFormat(id);
        if (ematch || pmatch) {
            return;
        }
        throw new InvalidParameterException("ID must be a valid email address or phone number");
    }
}
