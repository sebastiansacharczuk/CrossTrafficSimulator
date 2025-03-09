package com.example.crosstrafficsimulator;

import com.example.crosstrafficsimulator.simulation.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle("vehicle1", "north", "south");
    }

    @Test
    void testConstructorVehicle() {
        assertEquals("vehicle1", vehicle.getVehicleId());
        assertEquals("north_south", vehicle.getDestination());
        assertEquals(0, vehicle.getWaitingTime());
    }

    @Test
    void testIncreaseWaitingTime() {
        assertEquals(0, vehicle.getWaitingTime());
        vehicle.increaseWaitingTime(5);
        assertEquals(5, vehicle.getWaitingTime());
        vehicle.increaseWaitingTime(10);
        assertEquals(15, vehicle.getWaitingTime());
    }
}
