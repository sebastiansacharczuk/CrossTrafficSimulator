package com.example.crosstrafficsimulator;

import com.example.crosstrafficsimulator.simulation.IntersectionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class IntersectionControllerTest {
    private IntersectionController controller;

    @BeforeEach
    public void setUp() {
        controller = IntersectionController.getInstance();

    }

    @Test
    void testSingletonInstance() {
        IntersectionController anotherInstance = IntersectionController.getInstance();
        assertSame(controller, anotherInstance);
    }

}