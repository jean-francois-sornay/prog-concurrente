package com.enseirb.gl.IT310FP.chatbox;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Component
public class MessageRepository {
    private final Vector<Message> messages = new Vector<>();

    public MessageRepository() {
    }

    public List<Message> getTenLast() {
        int storageSize = messages.size();
        if(storageSize < 10) {
           return new ArrayList<>(messages);
        }
        return messages.subList(storageSize - 10, storageSize);
    }

    public void storeMessage(Message msg) {
        messages.add(msg);
    }
}
