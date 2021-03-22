package com.eagerforlife.traffic.client.traffic;

import com.eagerforlife.traffic.service.TrafficMessages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TrafficMessageClient {
    private final Logger logger = LoggerFactory.getLogger(TrafficMessageClient.class);

    // I would usually have this type of thing in config using ansible or similar
    private final String validSubCatagories = "Trafikstörning Vägarbete Trafikolycka";
    private final RestTemplate restTemplate;

    public TrafficMessageClient() {
        restTemplate = new RestTemplate();
    }

    public TrafficMessages getNotifications() {
        logger.info("Getting traffic messages");
        final String uri = "http://api.sr.se/api/v2/traffic/messages?pagination=false";
        final String results = restTemplate.getForObject(uri, String.class);
        TrafficMessagesResponse messagesXml = null;

        try {
            JAXBContext jaxBContext = JAXBContext.newInstance(TrafficMessagesResponse.class);
            Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
            messagesXml = (TrafficMessagesResponse) unmarshaller.unmarshal(new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8)));
        } catch (JAXBException e) {
           logger.error(e.toString());
        }

        if(messagesXml == null){
            return new TrafficMessages(new ArrayList<>());
        }

        logger.info("retrieved {} results", messagesXml.getMessageList().size());
        return new TrafficMessages(messagesXml.getMessageList()
                .stream()
                .filter(msg -> validSubCatagories.contains(msg.getDescription()))
                .map(TrafficMessageResponse::toTrafficMessage)
                .collect(Collectors.toList()));
    }

}
