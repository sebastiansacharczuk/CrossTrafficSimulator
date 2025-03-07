package com.example.crosstrafficsimulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CommandProcessor {
    public static int processCommandsFromFile(String inputFilePath, String outputFilePath) {
        IntersectionController controller = IntersectionController.getInstance();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(inputFilePath));
            JsonNode commandsNode = root.get("commands");

            if (commandsNode == null || !commandsNode.isArray()) {
                System.err.println("Error: Missing valid 'commands' list in JSON file");
                return 1;
            }

            ObjectNode outputRoot = mapper.createObjectNode();
            ArrayNode stepStatusesArray = mapper.createArrayNode();
            ArrayNode leftVehiclesArray = mapper.createArrayNode();

            for (JsonNode commandNode : commandsNode) {
                JsonNode typeNode = commandNode.get("type");

                if (typeNode != null && "addVehicle".equals(typeNode.asText())) {

                    String vehicleId = commandNode.get("vehicleId").asText();
                    String startRoad = commandNode.get("startRoad").asText();
                    String endRoad = commandNode.get("endRoad").asText();

                    List<String> validDirections = List.of("south", "east", "west", "north");

                    if (vehicleId == null || startRoad == null || endRoad == null) {
                        System.err.println("Validation failed: One or more fields are null. Shutting down...");
                        return 1;
                    }

                    if (vehicleId.isEmpty() || !validDirections.contains(startRoad) || !validDirections.contains(endRoad)) {
                        System.err.println("Validation failed: Invalid input values. Shutting down...");
                        return 1;
                    }

                    controller.addVehicleToLane(new Vehicle(vehicleId, startRoad, endRoad));


                }
                else if (typeNode != null && "step".equals(typeNode.asText())) {
                    for(Vehicle vehicle : controller.simulateStep()) {
                        leftVehiclesArray.add(vehicle.getVehicleId());
                    }
                    // controller.displayVehiclesOnLanes();
                    ObjectNode leftVehiclesObject = mapper.createObjectNode();
                    leftVehiclesObject.set("leftVehicles", leftVehiclesArray);

                    stepStatusesArray.add(leftVehiclesObject);
                    leftVehiclesArray = mapper.createArrayNode();

                }
            }



            outputRoot.set("stepStatuses", stepStatusesArray);
            mapper.writeValue(new File(outputFilePath), outputRoot);

        } catch (IOException e) {
            System.err.println("Error processing JSON file: " + e.getMessage());
            return 1;
        }
        return 0;
    }

}
