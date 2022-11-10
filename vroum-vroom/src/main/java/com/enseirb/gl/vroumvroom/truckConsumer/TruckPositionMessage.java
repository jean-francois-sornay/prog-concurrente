package com.enseirb.gl.vroumvroom.truckConsumer;

public class TruckPositionMessage {
    private Position position;
    private int truckid;
    private double ts;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getTruckid() {
        return truckid;
    }

    public void setTruckid(int truckid) {
        this.truckid = truckid;
    }

    public double getTs() {
        return ts;
    }

    public void setTs(double ts) {
        this.ts = ts;
    }
}
