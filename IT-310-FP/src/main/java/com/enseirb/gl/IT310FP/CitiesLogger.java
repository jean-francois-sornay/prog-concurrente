package com.enseirb.gl.IT310FP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CitiesLogger {
    public static final String TOPIC = "log";
    private final Logger log = LoggerFactory.getLogger(CitiesReceiver.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /*
    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationStarted() {
        log.info("Application starting");
        kafkaTemplate.send(TOPIC, "Désolé, à 13h30 on est en réunion :'( " + Instant.now());
    }
     */
}

