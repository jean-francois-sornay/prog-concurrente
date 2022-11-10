package com.enseirb.gl.vroumvroom;

import java.util.Date;

public class Truck {
    private String licensePlate;
    private Date firstCirculationDate;
    private int emptyWeight;

    public Truck(String licensePlate, Date firstCirculationDate, int emptyWeight) {
        this.licensePlate = licensePlate;
        this.firstCirculationDate = firstCirculationDate;
        this.emptyWeight = emptyWeight;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Date getFirstCirculationDate() {
        return firstCirculationDate;
    }

    public float getEmptyWeight() {
        return emptyWeight;
    }
}
