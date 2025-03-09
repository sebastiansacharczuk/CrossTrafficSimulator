package com.example.crosstrafficsimulator.simulation;

import java.util.ArrayList;
import java.util.List;

import static com.example.crosstrafficsimulator.simulation.IndependentPathsFinder.findMaximalIndependentSets;
import static com.example.crosstrafficsimulator.simulation.ManualSettings.*;


public class IntersectionController {

    private static IntersectionController instance;

    private List<Lane> lanes;

    private List<List<Integer>> greenLightsConfigurations;

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
            Lane lane = new Lane(laneNumber);
            result.add(lane);
        }
        return result;
    }

    public int addVehicleToLane(Vehicle vehicle) {
        List<Integer> laneNumbers = DIRECTION_TO_LANE_MAP.get(vehicle.getDestination());

        if (laneNumbers == null || laneNumbers.isEmpty()) {
            System.err.println("There are no available lanes for directions: " + vehicle.getDestination());
            return -1;
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
            vehicle.setLaneNumber(selectedLane.getLaneNumber());
            selectedLane.addVehicle(vehicle);
            System.out.println("Added vehicle " + vehicle.getVehicleId() + " to lane " + selectedLane.getLaneNumber());
            return selectedLane.getLaneNumber();
        } else {
            System.err.println("Lane not found for vehicle: " + vehicle.getVehicleId());
            return -1;
        }
    }

    public void increaseWaitingTimeForAllVehicles(int seconds) {
        for (Lane lane : lanes) {
            for (Vehicle vehicle : lane.getVehicles()) {
                vehicle.increaseWaitingTime(seconds);
            }
        }
    }

    private int calculateGreenTime(List<Integer> config) {
        int maxVehicles = 0;
        for (int laneNumber : config) {
            for (Lane lane : lanes) {
                if (lane.getLaneNumber() == laneNumber) {
                    int vehicleCount = lane.getVehicles().size();
                    maxVehicles = Math.max(maxVehicles, vehicleCount);
                    break;
                }
            }
        }
        return maxVehicles * SECONDS_PER_VEHICLE;
    }

    public void displayVehiclesOnLanes() {
        System.out.println("Condition of traffic:");
        for (Lane lane : lanes) {
            System.out.print("Lane " + lane.getLaneNumber() + ":");
            List<Vehicle> vehicles = lane.getVehicles();
            if (vehicles.isEmpty()) {
                System.out.println(" --");
            } else {
                for (Vehicle vehicle : vehicles) {
                    System.out.print(" " + vehicle.getVehicleId());
                }
                System.out.println();
            }
        }
        System.out.println("------------------------");
    }


    private double calculateLaneWeight(Lane lane) {
        List<Vehicle> vehicles = lane.getVehicles();
        if (vehicles.isEmpty()) {
            return 0.0;
        }

        double totalWaitingTime = 0;
        for (Vehicle vehicle : vehicles) {
            totalWaitingTime += vehicle.getWaitingTime();
        }
        double averageWaitingTime = totalWaitingTime / vehicles.size();
        double vehicleCountFactor = Math.sqrt(vehicles.size());
        return averageWaitingTime + vehicleCountFactor;
    }


    public List<Integer> selectNextConfiguration() {
        double maxTotalWeight = -1;
        List<Integer> selectedConfig = null;

        for (List<Integer> config : greenLightsConfigurations) {
            double totalWeight = 0;
            for (int laneNumber : config) {
                for (Lane lane : lanes) {
                    if (lane.getLaneNumber() == laneNumber) {
                        totalWeight += calculateLaneWeight(lane);
                        break;
                    }
                }
            }
            if (totalWeight > maxTotalWeight) {
                maxTotalWeight = totalWeight;
                selectedConfig = config;
            }
        }
        return selectedConfig != null ? selectedConfig : greenLightsConfigurations.get(0); // Domyślna konfiguracja, jeśli brak pojazdów
    }

    private List<Vehicle> activateConfiguration(List<Integer> config) {
        if (config == null) {
            System.err.println("There are no configurations.");
            return null;
        }

        int greenTime = calculateGreenTime(config);

        System.out.println("[GREEN LIGHT] on lanes: " + config + " for " + greenTime + " sec (algorithm weight: " + String.format("%.2f", calculateConfigWeight(config)) + ")");
        System.out.println("[YELLOW LIGHT] for " + SECONDS_YELLOW_LIGHT + " sec.");
        System.out.println("[RED LIGHT]");

        List<Vehicle> leftVehicles = new ArrayList<>();
        for (int laneNumber : config) {
            for (Lane lane : lanes) {
                if (lane.getLaneNumber() == laneNumber) {

                    List<Vehicle> removed = lane.removeFirstVehicles(Math.min(lane.getVehiclesCount(), MAX_VEHICLES_PER_GREEN_LIGHT));
                    leftVehicles.addAll(removed);

                    for (Vehicle vehicle : removed) {
                        System.out.println("Vehicle { id:" + vehicle.getVehicleId() + ", lane:" + laneNumber + ", waitingTime:" + vehicle.getWaitingTime() + "sec }");
                    }
                    break;
                }
            }
        }
        increaseWaitingTimeForAllVehicles(greenTime + SECONDS_YELLOW_LIGHT);
        return leftVehicles;
    }

    private double calculateConfigWeight(List<Integer> config) {
        double totalWeight = 0;
        for (int laneNumber : config) {
            for (Lane lane : lanes) {
                if (lane.getLaneNumber() == laneNumber) {
                    totalWeight += calculateLaneWeight(lane);
                    break;
                }
            }
        }
        return totalWeight;
    }

    public List<Vehicle> simulateStep() {
        System.out.println("simulation step");
        displayVehiclesOnLanes();
        List<Integer> nextConfig = selectNextConfiguration();
        return activateConfiguration(nextConfig);
    }
}
