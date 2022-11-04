package com.enseirb.gl.IT310FP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CitiesReceiver {

    private final Logger log = LoggerFactory.getLogger(CitiesReceiver.class);

    /*
    @KafkaListener(id= "jf-city", topics = "cities")
    public void received(String city) {
        log.info("City received {}", city);
    }
     */


}
