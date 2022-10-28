package com.enseirb.gl.IT310FP.chatbox;

import com.enseirb.gl.IT310FP.Truck;
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

    public static final String TOPIC = "msg.txt";

    private final Logger log = LoggerFactory.getLogger(String.class);

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ChatterboxController() {
    }

    @PostMapping
    public void post (@RequestBody String msg) {
        System.out.println(msg);
        kafkaTemplate.send(TOPIC, msg + " " + Instant.now());
    }

    @GetMapping("")
    public List<String> getLastMessages() {
        return messageRepository.getTenLast();
    }

    @KafkaListener(id= "msg-tlj", topics = TOPIC)
    public void received(String message) {
        log.info("message received {}", message);
        messageRepository.storeMessage(message);
    }
}
