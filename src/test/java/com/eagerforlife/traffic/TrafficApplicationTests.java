package com.eagerforlife.traffic;

import com.eagerforlife.traffic.controller.notification.NotificationController;
import com.eagerforlife.traffic.controller.registration.RegistrationController;
import com.eagerforlife.traffic.service.NotificationService;
import com.eagerforlife.traffic.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class TrafficApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(TrafficApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private NotificationService notificationService;

    @Autowired
    private RegistrationController registrationController;
    @Autowired
    private NotificationController notificationController;

    @Test
    void contextLoads() {
        assertThat(registrationController).isNotNull();
        assertThat(notificationController).isNotNull();
        assertThat(registrationService).isNotNull();
        assertThat(notificationService).isNotNull();
    }

    @Test
    void addGoodEmailClientTest() {
        final String jsonContent = "{ \"id\":\"test@test.com\",  \"latitude\" : 1234.43,  \"longitude\" : 4321.12 }";

        try {
            mockMvc.perform(post("/traffic/notifications/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assertions.fail("Exception" + e);
        }
    }

    @Test
    void addBadEmailClientTest() {
        final String jsonContent = "{ \"id\":\"test@test.com.\",  \"latitude\" : 1234.43,  \"longitude\" : 4321.12 }";

        try {
            mockMvc.perform(post("/traffic/notifications/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assertions.fail("Exception" + e);
        }
    }

    @Test
    void addGoodPhoneClientTest() {
        final String jsonContent = "{ \"id\":\"321 432 5323\",  \"latitude\" : 1234.43,  \"longitude\" : 4321.12 }";

        try {
            mockMvc.perform(post("/traffic/notifications/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assertions.fail("Exception" + e);
        }
    }

    @Test
    void addBadPhoneClientTest() {
        final String jsonContent = "{ \"id\":\"23434-32423-4324\",  \"latitude\" : 1234.43,  \"longitude\" : 4321.12 }";

        try {
            mockMvc.perform(post("/traffic/notifications/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent))
                    .andExpect(status().is4xxClientError());
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assertions.fail("Exception" + e);
        }
    }
}
