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
            inputFileName = "input1.json";
            outputFileName = "output.json";
        }

        CommandProcessor.processCommandsFromFile(inputFileName, outputFileName);


        List<List<Integer>> greenLightConfigurations = findMaximalIndependentSets(VEHICLE_PATHS);
        System.out.println(greenLightConfigurations.size() + " green light configurations");

        for (int i = 0; i < greenLightConfigurations.size(); i++) {
            System.out.print("Group[" + i + "]: ");
            for (int j = 0; j < greenLightConfigurations.get(i).size(); j++ ) {
                System.out.print(greenLightConfigurations.get(i).get(j) + " ");
            }
            System.out.println();
        }
        //        SpringApplication.run(CrossTrafficSimulatorApplication.class, args);
    }


}
