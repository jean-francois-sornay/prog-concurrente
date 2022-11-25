package com.enseirb.gl.vroumvroom.services;

import com.enseirb.gl.vroumvroom.models.Truck;
import com.enseirb.gl.vroumvroom.repositories.TruckRepository;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class TruckConsumer implements ConsumerSeekAware {
    private static final String TOPIC = "trucks.position";

    private final Logger log = LoggerFactory.getLogger(String.class);
    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckAlert truckAlert;

    public TruckConsumer() {}


    /**
     * When a partition is assigned, seek to a position of the partition depending on a timestamp
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        callback.seekToTimestamp(assignments.keySet(), Instant.now().minusSeconds(60 * 60).toEpochMilli());
    }


    /**
     * Listen to the topic "trucks.position" and save the truck in the database
     */
    @KafkaListener(id = "trucks.position", topics = TOPIC)
    public void received(Truck tpMessage) {
        log.info("message received {}", tpMessage);
        truckAlert.checkTruckPosition(tpMessage);
        truckRepository.storeTruckPosition(tpMessage);
    }
}
