package com.enseirb.gl.vroumvroom.services;

import com.enseirb.gl.vroumvroom.models.Truck;
import com.enseirb.gl.vroumvroom.utils.BreisenWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TruckAlert {

    @Autowired
    private KafkaTemplate<String, Truck> kafkaTemplate;
    @Autowired
    private BreisenWebService breisenWebService;

    /**
     * Check if the position of the truck is valid,
     * if not send an alert
     * @param truck the truck position message
     */
    @Async
    public void checkTruckPosition(Truck truck) {
        int shouldAlert = breisenWebService.sendRequestToBreisenWebService("/position/check", truck, Integer.class);
        if (shouldAlert > 0) {
            kafkaTemplate.send("trucks.alert", String.valueOf(truck.getTruckId()), truck);
        }
    }
}
