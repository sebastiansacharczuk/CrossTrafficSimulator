package com.example.crosstrafficsimulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class CommandProcessor {
    public static void processCommandsFromFile(String inputFilePath, String outputFilePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(inputFilePath));
            JsonNode commandsNode = root.get("commands");

            if (commandsNode == null || !commandsNode.isArray()) {
                System.err.println("Błąd: Brak poprawnej listy 'commands' w pliku JSON");
                return;
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
                    if (vehicleId != null && startRoad != null && endRoad != null) {
                        System.out.println(vehicleId);
                        leftVehiclesArray.add(vehicleId);

                    }
                }
                else if (typeNode != null && "step".equals(typeNode.asText())) {

                    ObjectNode leftVehiclesObject = mapper.createObjectNode();
                    leftVehiclesObject.set("leftVehicles", leftVehiclesArray);

                    stepStatusesArray.add(leftVehiclesObject);
                    leftVehiclesArray = mapper.createArrayNode();

                }
            }



            outputRoot.set("stepStatuses", stepStatusesArray);
            mapper.writeValue(new File(outputFilePath), outputRoot);

        } catch (IOException e) {
            System.err.println("Błąd podczas przetwarzania pliku JSON: " + e.getMessage());
        }
    }

}
