package com.enseirb.gl.vroumvroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TruckConsumer {
    public static final String TOPIC = "trucks.position";

    private final Logger log = LoggerFactory.getLogger(String.class);
    @Autowired private KafkaTemplate<String, Truck> kafkaTemplate;
    @Autowired private TruckRepository truckRepository;

    public TruckConsumer() {}

    @KafkaListener(id = "trucks.position", topics = TOPIC)
    public void received(Truck tpMessage) {
        log.info("message received {}", tpMessage);
        truckRepository.storeTruckPosition(tpMessage);
    }
}
