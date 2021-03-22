package com.eagerforlife.traffic.client.traffic;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sr")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrafficMessagesResponse {

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    private List<TrafficMessageResponse> messageList;

    public TrafficMessagesResponse() {
    }

    List<TrafficMessageResponse> getMessageList() {
        return messageList;
    }


}

