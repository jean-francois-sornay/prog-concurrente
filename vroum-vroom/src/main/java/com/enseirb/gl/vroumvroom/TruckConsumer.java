package com.enseirb.gl.vroumvroom;

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
import java.time.temporal.TemporalField;
import java.util.Map;

@Service
public class TruckConsumer implements ConsumerSeekAware {
    public static final String TOPIC = "trucks.position";

    private final Logger log = LoggerFactory.getLogger(String.class);
    @Autowired private KafkaTemplate<String, Truck> kafkaTemplate;
    @Autowired private TruckRepository truckRepository;

    public TruckConsumer() {}

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        long oneHour = 60 * 60;
        callback.seekToTimestamp(assignments.keySet(), Instant.now().getLong(ChronoField.INSTANT_SECONDS) - oneHour);
    }

    @KafkaListener(id = "trucks.position", topics = TOPIC)
    public void received(Truck tpMessage) {
        log.info("message received {}", tpMessage);
        truckRepository.storeTruckPosition(tpMessage);
    }
}
