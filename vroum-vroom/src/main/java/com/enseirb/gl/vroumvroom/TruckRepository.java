package com.enseirb.gl.vroumvroom;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TruckRepository {
    private final ConcurrentHashMap<Integer, Position> positions = new ConcurrentHashMap<>();

    public TruckRepository() {}

    public void storeTruckPosition(Truck truck) {
        positions.put(truck.getTruckId(), truck.getPosition());
    }
}
