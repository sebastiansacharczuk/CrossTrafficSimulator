package com.example.crosstrafficsimulator;

import com.example.crosstrafficsimulator.simulation.CommandProcessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class SimulationController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/simulation")
    public String getSimulation() {
        return "simulation";
    }

    @PostMapping(value = "/submit-commands", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> submitCommands(@RequestBody JsonNode jsonInput) {
        try {
            JsonNode commandsNode = jsonInput.get("commands");
            if (commandsNode == null || !commandsNode.isArray()) {
                throw new IllegalArgumentException("Missing valid 'commands' list in JSON");
            }

            ObjectNode result = CommandProcessor.processCommands(commandsNode);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Symulacja zakończona pomyślnie",
                    "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}