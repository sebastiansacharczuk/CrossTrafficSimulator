package com.example.crosstrafficsimulator;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    private final int laneNumber;
    private List<Vehicle> vehicles;

    public Lane(int laneNumber) {
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

    public int getLaneNumber() {
        return laneNumber;
    }

    public List<Vehicle> removeFirstVehicles(int amount) {
        List<Vehicle> removedVehicles = new ArrayList<>();
        int vehiclesToRemove = Math.min(amount, this.getVehiclesCount());

        for (int i = 0; i < vehiclesToRemove; i++) {
            Vehicle vehicle = this.vehicles.get(0);
            removedVehicles.add(vehicle);
            this.vehicles.remove(0);
        }

        return removedVehicles;
    }
}
