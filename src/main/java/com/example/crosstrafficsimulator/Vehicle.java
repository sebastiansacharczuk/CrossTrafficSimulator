package com.example.crosstrafficsimulator;


public class Vehicle {
    private final String vehicleId;
    private final String destination;
    private int waitingTime;

    public Vehicle(String vehicleId, String startRode, String endRode) {
        this.vehicleId = vehicleId;
        this.destination = startRode.toLowerCase() + "_" + endRode.toLowerCase();
        this.waitingTime = 0;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Car{ vehicleId= "+ this.vehicleId + ", destination " + this.destination + ", waitingTime" +  this.waitingTime + " }";
    }

    public void increaseWaitingTime(int seconds) {
        waitingTime += seconds;
    }
}
