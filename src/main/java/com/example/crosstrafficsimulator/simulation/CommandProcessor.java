package com.example.crosstrafficsimulator.simulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandProcessor {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectNode processCommands(JsonNode commandsNode) throws IOException {
        IntersectionController controller = IntersectionController.getInstance();

        if (commandsNode == null || !commandsNode.isArray()) {
            throw new IllegalArgumentException("Missing valid 'commands' list in JSON");
        }

        ObjectNode outputRoot = mapper.createObjectNode();
        ArrayNode stepStatusesArray = mapper.createArrayNode();

        // Mapa przechowująca liczbę pojazdów na pasach
        Map<Integer, Integer> vehiclesOnLanes = new HashMap<>();

        for (JsonNode commandNode : commandsNode) {
            JsonNode typeNode = commandNode.get("type");

            if (typeNode != null && "addVehicle".equals(typeNode.asText())) {
                String vehicleId = commandNode.get("vehicleId").asText();
                String startRoad = commandNode.get("startRoad").asText();
                String endRoad = commandNode.get("endRoad").asText();

                List<String> validDirections = List.of("south", "east", "west", "north");

                if (vehicleId == null || startRoad == null || endRoad == null) {
                    throw new IllegalArgumentException("One or more fields are null");
                }

                if (vehicleId.isEmpty() || !validDirections.contains(startRoad) || !validDirections.contains(endRoad)) {
                    throw new IllegalArgumentException("Invalid input values");
                }

                int laneNumber = controller.addVehicleToLane(new Vehicle(vehicleId, startRoad, endRoad));
                vehiclesOnLanes.put(laneNumber, vehiclesOnLanes.getOrDefault(laneNumber, 0) + 1);

            } else if (typeNode != null && "step".equals(typeNode.asText())) {
                // Tworzenie obiektu stepStatus dla tego kroku
                ObjectNode stepStatus = mapper.createObjectNode();

                // Dodanie vehiclesOnLanes jako obiekt z mapą pasów na liczbę pojazdów
                ObjectNode vehiclesOnLanesNode = mapper.createObjectNode();
                for (Map.Entry<Integer, Integer> entry : vehiclesOnLanes.entrySet()) {
                    vehiclesOnLanesNode.put(String.valueOf(entry.getKey()), entry.getValue());
                }
                stepStatus.set("vehiclesOnLanes", vehiclesOnLanesNode);

                // Dodanie leftVehicles jako tablicę obiektów z laneNumber i vehicleId
                ArrayNode leftVehiclesArray = mapper.createArrayNode();
                List<Vehicle> leftVehicles = controller.simulateStep();
                for (Vehicle vehicle : leftVehicles) {
                    ObjectNode vehicleInfo = mapper.createObjectNode();
                    vehicleInfo.put("laneNumber", vehicle.getLaneNumber());
                    vehicleInfo.put("vehicleId", vehicle.getVehicleId());
                    vehicleInfo.put("directions", vehicle.getDestination());
                    leftVehiclesArray.add(vehicleInfo);
                }
                stepStatus.set("leftVehicles", leftVehiclesArray);

                // Dodanie stepStatus do stepStatusesArray
                stepStatusesArray.add(stepStatus);

                // Wyczyść dane dla następnego kroku
                vehiclesOnLanes.clear();
            }
        }

        outputRoot.set("stepStatuses", stepStatusesArray);
        return outputRoot;
    }
    public static int processCommandsFromFile(String inputFilePath, String outputFilePath) {
        try {
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                System.err.println("Error: Input file '" + inputFilePath + "' not found.");
                return 1;
            }

            JsonNode root = mapper.readTree(inputFile);
            JsonNode commandsNode = root.get("commands");

            ObjectNode outputRoot = processCommands(commandsNode);
            mapper.writeValue(new File(outputFilePath), outputRoot);
            return 0;
        } catch (IOException e) {
            System.err.println("Error processing JSON file: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
    }
}