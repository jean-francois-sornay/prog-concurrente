package com.enseirb.gl.vroumvroom;

public class Truck {
    private Position position;
    private int truckId = -1;
    private double ts = 0;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public double getTs() {
        return ts;
    }

    public void setTs(double ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "TruckPositionMessage{" +
                "position=" + position +
                ", truckid=" + truckId +
                ", ts=" + ts +
                '}';
    }
}
