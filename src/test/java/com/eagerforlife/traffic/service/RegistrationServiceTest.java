package com.eagerforlife.traffic.service;

import com.eagerforlife.traffic.repository.ClientPosition;
import com.eagerforlife.traffic.repository.ClientRepository;
import com.eagerforlife.traffic.repository.RegisteredClient;
import com.eagerforlife.traffic.scheduler.DeregisterScheduler;
import com.eagerforlife.traffic.utility.ClientIdValidator;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.joda.time.DateTimeZone.UTC;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private DeregisterScheduler deregisterScheduler;

    @Test
    void registerUserTest() {
        final ClientRepository clientRepository = new ClientRepository(deregisterScheduler);
        final RegistrationService registrationService = new RegistrationService(clientRepository);
        final RegisteredClient client = new RegisteredClient("test@test.com", new ClientPosition(1.23d, 3.21d), DateTime.now(UTC), ClientIdValidator.EMAIL);

        registrationService.registerClient(client.getClientId(), client.getClientPosition(), client.getNotificationType());
        final RegisteredClient resultClient = clientRepository.getClient(client.getClientId());

        assertThat(resultClient).isNotNull();
        assertThat(resultClient.getClientId()).isEqualTo(client.getClientId());
        assertThat(resultClient.getClientPosition()).isEqualTo(client.getClientPosition());
        assertThat(resultClient.getNotificationType()).isEqualTo(client.getNotificationType());

        // Registration times will be different since ClientRepository provides the registration time.
        assertThat(resultClient.getRegistrationTime()).isNotEqualTo(client.getRegistrationTime());

    }

    @Test
    void isClientRegisteredTest() {
        final ClientRepository clientRepository = new ClientRepository(deregisterScheduler);
        final RegistrationService registrationService = new RegistrationService(clientRepository);
        final RegisteredClient client = new RegisteredClient("test@test.com", new ClientPosition(1.23d, 3.21d), DateTime.now(UTC), ClientIdValidator.EMAIL);

        // verify the client isn't registered
        assertThat(registrationService.isRegistered(client.getClientId())).isFalse();

        // register the client
        registrationService.registerClient(client.getClientId(), client.getClientPosition(), client.getNotificationType());
        final RegisteredClient resultClient = clientRepository.getClient(client.getClientId());

        // verify client is registered
        assertThat(registrationService.isRegistered(client.getClientId())).isTrue();
    }

    @Test
    void deregisterClientTest() {
        final ClientRepository clientRepository = new ClientRepository(deregisterScheduler);
        final RegistrationService registrationService = new RegistrationService(clientRepository);
        final RegisteredClient client = new RegisteredClient("test@test.com", new ClientPosition(1.23d, 3.21d), DateTime.now(UTC), ClientIdValidator.EMAIL);

        // register the client
        registrationService.registerClient(client.getClientId(), client.getClientPosition(), client.getNotificationType());
        final RegisteredClient resultClient = clientRepository.getClient(client.getClientId());

        // verify client is registered
        assertThat(registrationService.isRegistered(client.getClientId())).isTrue();

        // deregister the client
        registrationService.deleteRegistration(client.getClientId());

        // verify the client isn't registered
        assertThat(registrationService.isRegistered(client.getClientId())).isFalse();
    }


}
