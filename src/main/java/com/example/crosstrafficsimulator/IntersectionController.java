package com.example.crosstrafficsimulator;

import java.util.ArrayList;
import java.util.List;

import static com.example.crosstrafficsimulator.IndependentPathsFinder.findMaximalIndependentSets;
import static com.example.crosstrafficsimulator.ManualSettings.*;


public class IntersectionController {

    private static IntersectionController instance;

    private List<Lane> lanes;

    private List<List<List<Integer>>> greenLightsConfigurations;

    private IntersectionController() {
        lanes = setLanesFromSettings();
        greenLightsConfigurations = findMaximalIndependentSets(VEHICLE_PATHS);
    }

    public static IntersectionController getInstance() {
        if (instance == null) {
            instance = new IntersectionController();
        }
        return instance;
    }

    private List<Lane> setLanesFromSettings() {
        List<Lane> result = new ArrayList<>();

        for (List<Integer> path : VEHICLE_PATHS) {
            int laneNumber = path.get(0);
            Lane lane = new Lane(laneNumber, new ArrayList<>());
            result.add(lane);
        }
        return result;
    }

    public void addVehicleToLane(Vehicle vehicle) {
        List<Integer> laneNumbers = DIRECTION_TO_LANE_MAP.get(vehicle.getDestination());

        if (laneNumbers == null || laneNumbers.isEmpty()) {
            System.out.println("Brak dostępnych pasów dla kierunku: " + vehicle.getDestination());
            return;
        }

        Lane selectedLane = null;
        int minVehicles = Integer.MAX_VALUE;

        for (Lane lane : lanes) {
            if (laneNumbers.contains(lane.getLaneNumber())) {
                int vehicleCount = lane.getVehiclesCount();
                if (vehicleCount < minVehicles) {
                    minVehicles = vehicleCount;
                    selectedLane = lane;
                }
            }
        }

        if (selectedLane != null) {
            selectedLane.addVehicle(vehicle);
            System.out.println("Dodano pojazd " + vehicle.getVehicleId() + " do pasa " + selectedLane.getLaneNumber());
        } else {
            System.out.println("Nie znaleziono pasującego pasa dla pojazdu " + vehicle.getVehicleId());
        }
    }

    public void displayVehiclesOnLanes() {
        System.out.println("Stan pojazdów na pasach:");
        for (Lane lane : lanes) {
            System.out.println("Pas " + lane.getLaneNumber() + ":");
            List<Vehicle> vehicles = lane.getVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("  Brak pojazdów.");
            } else {
                for (Vehicle vehicle : vehicles) {
                    System.out.println("  " + vehicle.toString());
                }
            }
        }
        System.out.println("------------------------");
    }
}
