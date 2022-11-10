package com.enseirb.gl.vroumvroom.truckConsumer;

import org.springframework.stereotype.Component;
import java.util.Vector;

@Component
public class PositionRepository {
    private final Vector<TruckPositionMessage> positions = new Vector<>();

    public PositionRepository() {}

    public void storePosition(TruckPositionMessage posMessage) {
        positions.add(posMessage);
    }
}
