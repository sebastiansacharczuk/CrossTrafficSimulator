package com.example.crosstrafficsimulator.simulation;

import java.util.*;

public class ManualSettings {

    // All paths vehicle can take in interception
    // First element: entry lane
    // Last element: exit lane
    // Other elements: points of collision with other paths
    public static final List<List<Integer>> VEHICLE_PATHS = List.of(
            List.of(0, 6, 17, 20, 25),
            List.of(1, 7, 12, 21, 26),
            List.of(2, 8, 14, 18),
            List.of(16, 17, 12, 8, 3),
            List.of(19, 20, 21, 22, 23, 24),
            List.of(27, 21, 17, 11),
            List.of(28, 22, 13, 8, 3),
            List.of(29, 23, 14, 9, 4),
            List.of(15, 14, 13, 21, 26),
            List.of(10, 9, 8, 7, 6, 5)
    );

    // KEY: vehicle route in the format "start_end"
    // VALUE: list of road numbers
    public static final Map<String, List<Integer>> DIRECTION_TO_LANE_MAP = new HashMap<>();
    static {
        DIRECTION_TO_LANE_MAP.put("south_west", List.of(27));
        DIRECTION_TO_LANE_MAP.put("south_north", List.of(28, 29));
        DIRECTION_TO_LANE_MAP.put("south_east", List.of(29));

        DIRECTION_TO_LANE_MAP.put("east_south", List.of(15));
        DIRECTION_TO_LANE_MAP.put("east_west", List.of(10));
        DIRECTION_TO_LANE_MAP.put("east_north", List.of(10));

        DIRECTION_TO_LANE_MAP.put("north_east", List.of(2));
        DIRECTION_TO_LANE_MAP.put("north_south", List.of(0, 1));
        DIRECTION_TO_LANE_MAP.put("north_west", List.of(0));

        DIRECTION_TO_LANE_MAP.put("west_north", List.of(16));
        DIRECTION_TO_LANE_MAP.put("west_east", List.of(19));
        DIRECTION_TO_LANE_MAP.put("west_south", List.of(19));
    }


    public static final int SECONDS_PER_VEHICLE = 7;
    public static final int SECONDS_YELLOW_LIGHT = 4;
    public static final int MAX_VEHICLES_PER_GREEN_LIGHT = 5;

}