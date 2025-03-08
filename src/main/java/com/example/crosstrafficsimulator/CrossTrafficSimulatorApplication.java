package com.example.crosstrafficsimulator;

import java.util.List;

import static com.example.crosstrafficsimulator.IndependentPathsFinder.findMaximalIndependentSets;
import static com.example.crosstrafficsimulator.ManualSettings.VEHICLE_PATHS;
//@SpringBootApplication
public class CrossTrafficSimulatorApplication {

    public static void main(String[] args) {

        String inputFileName;
        String outputFileName;

        if (args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        }
        else {
            inputFileName = "input.json";
            outputFileName = "output.json";
        }



        List<List<Integer>> greenLightConfigurations = findMaximalIndependentSets(VEHICLE_PATHS);
        System.out.println(greenLightConfigurations.size() + " green light configurations ([index]: lane_0, lane_1 etc...)");

        for (int i = 0; i < greenLightConfigurations.size(); i++) {
            System.out.print("[" + i + "]: ");
            for (int j = 0; j < greenLightConfigurations.get(i).size(); j++ ) {
                System.out.print(greenLightConfigurations.get(i).get(j) + " ");
            }
            System.out.println();
        }


        int resultCode = CommandProcessor.processCommandsFromFile(inputFileName, outputFileName);
        if (resultCode == 0) {
            System.out.println("Simulation succeed");
        }
        else {
            System.out.println("Simulation failed");
        }



        //        SpringApplication.run(CrossTrafficSimulatorApplication.class, args);
    }


}
