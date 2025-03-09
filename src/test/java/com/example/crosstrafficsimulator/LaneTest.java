package com.example.crosstrafficsimulator;
import com.example.crosstrafficsimulator.simulation.Lane;
import com.example.crosstrafficsimulator.simulation.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaneTest {
    private Lane lane;

    @BeforeEach
    void setUp() {
        lane = new Lane(1);
        lane.addVehicle(new Vehicle("vehicle1", "north", "south"));
        lane.addVehicle(new Vehicle("vehicle2", "east", "west"));
        lane.addVehicle(new Vehicle("vehicle3", "east", "north"));
        lane.addVehicle(new Vehicle("vehicle4", "east", "south"));
        lane.addVehicle(new Vehicle("vehicle5", "north", "west"));
        lane.addVehicle(new Vehicle("vehicle6", "east", "west"));
    }

    @Test
    void testRemoveFirstVehicles() {
        List<Vehicle> result = lane.removeFirstVehicles(1);
        assertEquals(1, result.size());
        assertEquals("vehicle1", result.get(0).getVehicleId());
        assertEquals(5, lane.getVehiclesCount());
    }

    @Test
    void testRemoveMoreVehiclesThanAvailable() {
        lane.removeFirstVehicles(lane.getVehiclesCount() + 1);

        assertTrue(lane.getVehicles().isEmpty());
        assertEquals(0, lane.getVehiclesCount());
    }

    @Test
    void testRemoveVehiclesFromEmptyLane() {
        lane.removeFirstVehicles(lane.getVehiclesCount());

        assertTrue(lane.getVehicles().isEmpty());
        assertEquals(0, lane.getVehiclesCount());
    }
}
