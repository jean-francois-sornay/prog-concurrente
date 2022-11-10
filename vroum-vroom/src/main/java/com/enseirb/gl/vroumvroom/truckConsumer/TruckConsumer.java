package com.enseirb.gl.vroumvroom.truckConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/messages")
public class TruckConsumer {
    public static final String TOPIC = "";
    private final Logger log = LoggerFactory.getLogger(String.class);
    @Autowired private KafkaTemplate<String, TruckPositionMessage> kafkaTemplate;
    @Autowired private PositionRepository positionRepository;

    public TruckConsumer() {}

    @KafkaListener(id = "trucks.position", topics = "trucks.position")
    public void received(TruckPositionMessage tpMessage) {
        log.info("message received {}", tpMessage);
        //positionRepository.storePosition(tpMessage);
    }
}
