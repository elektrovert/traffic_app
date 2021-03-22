package com.eagerforlife.traffic.client.traffic;

import com.eagerforlife.traffic.service.TrafficMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrafficMessageResponse {

    private LocalDateTime createdDate;
    private String title;
    private String description;
    private float latitude;
    private float longitude;
    private String subcategory;
    private int priority;

    TrafficMessageResponse() {
    }

    public String getDescription() {
        return description;
    }

    public TrafficMessage toTrafficMessage() {
        return new com.eagerforlife.traffic.service.TrafficMessage(createdDate, title, description, latitude, longitude, subcategory, priority);
    }
}
