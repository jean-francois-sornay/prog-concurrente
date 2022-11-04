package com.enseirb.gl.IT310FP.chatbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class ChatterboxController {

    public static final String TOPIC = "msg.json.tlj";

    private final Logger log = LoggerFactory.getLogger(String.class);

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public ChatterboxController() {
    }

    @PostMapping
    public void post (@RequestBody Message msg) {
        System.out.println(msg);
        kafkaTemplate.send(TOPIC, msg);
    }

    @GetMapping("")
    public List<Message> getLastMessages() {
        return messageRepository.getTenLast();
    }

    @KafkaListener(id= "msg-tlj", topics = TOPIC)
    public void received(Message message) {
        log.info("message received {}", message);
        messageRepository.storeMessage(message);
    }
}
