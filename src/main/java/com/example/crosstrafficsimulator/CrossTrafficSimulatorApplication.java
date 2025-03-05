package com.example.crosstrafficsimulator;

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
            outputFileName = "output2.json";
        }



        CommandProcessor.processCommandsFromFile(inputFileName, outputFileName);

        //        SpringApplication.run(CrossTrafficSimulatorApplication.class, args);
    }


}
