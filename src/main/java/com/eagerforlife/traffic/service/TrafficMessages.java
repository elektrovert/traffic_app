package com.eagerforlife.traffic.service;

import java.util.List;

public class TrafficMessages {
    private final List<TrafficMessage> messageList;

    public TrafficMessages(List<TrafficMessage> messageList){
        this.messageList = messageList;
    }

    public List<TrafficMessage> getMessageList(){
        return messageList;
    }

}

