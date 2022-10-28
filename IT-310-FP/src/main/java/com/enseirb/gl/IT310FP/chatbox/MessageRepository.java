package com.enseirb.gl.IT310FP.chatbox;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageRepository {
    private final List<String> messages = new ArrayList<>();

    public MessageRepository() {
    }

    public List<String> getTenLast() {
        int storageSize = messages.size();
        if(storageSize < 10) {
           return new ArrayList<>(messages);
        }
        return messages.subList(storageSize - 10, storageSize);
    }

    public void storeMessage(String msg) {
        messages.add(msg);
    }
}
