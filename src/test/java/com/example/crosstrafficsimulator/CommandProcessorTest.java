package com.example.crosstrafficsimulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorTest {

    private ObjectMapper mapper = new ObjectMapper();

    @TempDir
    Path tempDir;

    private File inputFile;
    private File outputFile;

    @BeforeEach
    public void setUp() throws IOException {
        inputFile = tempDir.resolve("input.json").toFile();
        outputFile = tempDir.resolve("output.json").toFile();
    }

    @Test
    public void testProcessCommandsValidInput() throws IOException {
        String inputJson = "{\"commands\": [" +
                "{\"type\": \"addVehicle\", \"vehicleId\": \"vehicle1\", \"startRoad\": \"south\", \"endRoad\": \"west\"}," +
                "{\"type\": \"step\"}" +
                "]}";
        mapper.writeValue(inputFile, mapper.readTree(inputJson));

        int result = CommandProcessor.processCommandsFromFile(inputFile.getPath(), outputFile.getPath());
        assertEquals(0, result);

        JsonNode output = mapper.readTree(outputFile);
        JsonNode stepStatuses = output.get("stepStatuses");
        assertEquals(1, stepStatuses.size());
        JsonNode leftVehicles = stepStatuses.get(0).get("leftVehicles");
        assertEquals(1, leftVehicles.size());
        assertEquals("vehicle1", leftVehicles.get(0).asText());
    }

    @Test
    public void testProcessCommandsMissingInputFile() {
        String nonExistentFile = tempDir.resolve("nonexistent.json").toString();
        int result = CommandProcessor.processCommandsFromFile(nonExistentFile, outputFile.getPath());
        assertEquals(1, result);
    }

    @Test
    public void testProcessCommandsInvalidJsonNoCommands() throws IOException {
        mapper.writeValue(inputFile, mapper.createObjectNode());
        int result = CommandProcessor.processCommandsFromFile(inputFile.getPath(), outputFile.getPath());
        assertEquals(1, result);
    }

    @Test
    public void testProcessCommandsInvalidVehicleData() throws IOException {
        String inputJson = "{\"commands\": [" +
                "{\"type\": \"addVehicle\", \"vehicleId\": \"\", \"startRoad\": \"south\", \"endRoad\": \"west\"}" +
                "]}";
        mapper.writeValue(inputFile, mapper.readTree(inputJson));

        int result = CommandProcessor.processCommandsFromFile(inputFile.getPath(), outputFile.getPath());
        assertEquals(1, result);
    }

    @Test
    public void testProcessCommandsStepWithNoVehicles() throws IOException {
        String inputJson = "{\"commands\": [{\"type\": \"step\"}]}";
        mapper.writeValue(inputFile, mapper.readTree(inputJson));

        int result = CommandProcessor.processCommandsFromFile(inputFile.getPath(), outputFile.getPath());
        assertEquals(0, result);

        JsonNode output = mapper.readTree(outputFile);
        JsonNode stepStatuses = output.get("stepStatuses");
        assertEquals(1, stepStatuses.size());
        JsonNode leftVehicles = stepStatuses.get(0).get("leftVehicles");
        assertTrue(leftVehicles.isArray());
        assertEquals(0, leftVehicles.size());
    }
}