package com.example.crosstrafficsimulator;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    private final int laneNumber;
    private List<Vehicle> vehicles;

    public Lane(int laneNumber, List<String> directions) {
        this.laneNumber = laneNumber;
        this.vehicles = new ArrayList<>();
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public int getVehiclesCount() {
        return vehicles.size();
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
    }

    public int getLaneNumber() {
        return laneNumber;
    }
}
