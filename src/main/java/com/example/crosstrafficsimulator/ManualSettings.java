package com.example.crosstrafficsimulator;

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

    // All intersection lanes
    // Each lane has number and list of destinations (format: start_end)
    public static final List<Lane> INTERSECTION_LANES = new ArrayList<>();
    static {
        INTERSECTION_LANES.add(new Lane(0, List.of("north_south", "north_west")));
        INTERSECTION_LANES.add(new Lane(1, List.of("north_south")));
        INTERSECTION_LANES.add(new Lane(2, List.of("north_east")));

        INTERSECTION_LANES.add(new Lane(10, List.of("east_west", "east_north")));
        INTERSECTION_LANES.add(new Lane(15, List.of("east_south")));

        INTERSECTION_LANES.add(new Lane(27, List.of("south_west")));
        INTERSECTION_LANES.add(new Lane(28, List.of("south_north")));
        INTERSECTION_LANES.add(new Lane(29, List.of("south_north", "south_east")));

        INTERSECTION_LANES.add(new Lane(16, List.of("west_north")));
        INTERSECTION_LANES.add(new Lane(19, List.of("west_east", "west_south")));
    }

    // Helper for assigning to lane based on vehicle's destination
    public static final Map<String, List<Integer>> INTERSECTION_MAP = new HashMap<>();
    static {
        INTERSECTION_MAP.put("north_south", List.of(0, 1));
        INTERSECTION_MAP.put("north_west",  List.of(0));
        INTERSECTION_MAP.put("north_east",  List.of(2));

        INTERSECTION_MAP.put("east_west", List.of(10));
        INTERSECTION_MAP.put("east_north",  List.of(10));
        INTERSECTION_MAP.put("east_south",  List.of(15));

        INTERSECTION_MAP.put("south_west",  List.of(27));
        INTERSECTION_MAP.put("south_north", List.of(28, 29));
        INTERSECTION_MAP.put("south_east",  List.of(29));

        INTERSECTION_MAP.put("west_north",  List.of(16));
        INTERSECTION_MAP.put("west_east", List.of(19));
        INTERSECTION_MAP.put("west_south",  List.of(19));
    }

}