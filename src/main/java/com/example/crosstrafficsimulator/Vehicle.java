package com.example.crosstrafficsimulator;


public class Vehicle {
    private String vehicleId;
    private String destination;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }



    public Vehicle(String vehicleId, String startRode, String endRode) {
        this.vehicleId = vehicleId;
        this.destination = startRode.toLowerCase() + "_" + endRode.toLowerCase();
    }


    @Override
    public String toString() {
        return "Car{vehicleId= "+ vehicleId + "}";
    }
}
