package com.enseirb.gl.vroumvroom.services;

import com.enseirb.gl.vroumvroom.models.Position;
import com.enseirb.gl.vroumvroom.models.Truck;
import com.enseirb.gl.vroumvroom.repositories.TruckRepository;
import com.enseirb.gl.vroumvroom.utils.BreisenWebService;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Map;

@Service
public class TruckConsumer implements ConsumerSeekAware {
    private static final String TOPIC = "trucks.position";

    private final Logger log = LoggerFactory.getLogger(String.class);
    @Autowired
    private KafkaTemplate<String, Truck> kafkaTemplate;
    @Autowired
    private TruckRepository truckRepository;

    public TruckConsumer() {}


    /**
     * When a partition is assigned, seek to a position of the partition depending on a timestamp
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        long oneHour = 60 * 60;
        callback.seekToTimestamp(assignments.keySet(), Instant.now().getLong(ChronoField.INSTANT_SECONDS) - oneHour);
    }


    /**
     * Listen to the topic "trucks.position" and save the truck in the database
     */
    @KafkaListener(id = "trucks.position", topics = TOPIC)
    public void received(Truck tpMessage) {
        log.info("message received {}", tpMessage);
        checkTruckPosition(tpMessage);
        truckRepository.storeTruckPosition(tpMessage);
    }


    /**
     * Check if the position of the truck is valid,
     * if not send an alert
     * @param truck the truck position message
     */
    private void checkTruckPosition(Truck truck) {
        int shouldAlert = BreisenWebService.sendRequestToBreisenWebService("/position/check", truck, Integer.class);
        if (shouldAlert > 0) {
            kafkaTemplate.send("trucks.alert", String.valueOf(truck.getTruckId()), truck);
        }
    }
}
