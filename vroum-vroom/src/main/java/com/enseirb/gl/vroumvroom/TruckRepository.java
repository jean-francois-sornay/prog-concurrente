package com.enseirb.gl.vroumvroom;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TruckRepository {
    private final ConcurrentHashMap<Integer, Truck> positions = new ConcurrentHashMap<>();

    public TruckRepository() {
    }

    public synchronized void storeTruckPosition(Truck truck) {
        if (positions.containsKey(truck.getTruckId())) {
            if (positions.get(truck.getTruckId()).getTs() > truck.getTs()) {
                return;
            }
        }
        positions.put(truck.getTruckId(), truck);
    }

    public Truck getLastPosition(int id) {
        if (!positions.containsKey(id)) {
            throw new TruckNotFoundException("truck with id " + id + " not found");
        }
        return positions.get(id);
    }
}
