package com.enseirb.gl.vroumvroom.repositories;

import com.enseirb.gl.vroumvroom.errors.TruckNotFoundException;
import com.enseirb.gl.vroumvroom.models.Position;
import com.enseirb.gl.vroumvroom.models.Truck;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TruckRepository {
    // Map of TruckID -> Truck (containing the last position)
    private final ConcurrentHashMap<Integer, Truck> positions = new ConcurrentHashMap<>();

    public TruckRepository() {}


    /**
     * Store a new position for a truck
     * Remark: if the truck is not known, it is created
     */
    public synchronized void storeTruckPosition(Truck truck) {
        if (positions.containsKey(truck.getTruckId()) && positions.get(truck.getTruckId()).getTs() > truck.getTs()) {
                return;
        }
        positions.put(truck.getTruckId(), truck);
    }


    /**
     * Get all trucks known
     */
    public synchronized List<Truck> getTrucks() {
        return List.copyOf(positions.values());
    }


    /**
     * Get the last known position of a truck
     * @param id the id of the truck
     */
    public synchronized Position getTruckLastPosition(int id) {
        if (!positions.containsKey(id)) {
            throw new TruckNotFoundException("truck with id " + id + " not found");
        }
        return positions.get(id).getPosition();
    }
}
