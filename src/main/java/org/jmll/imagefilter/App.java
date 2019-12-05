package org.jmll.imagefilter;

import org.apache.commons.cli.*;

import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.Filters.*;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

    //Define the log file
    public static String logFilename;

    public static void main(String[] args) {

        // print given arguments to console
        System.out.println("Current arguments: " + Arrays.toString(args));

        // create Options object
        Options options = new Options();

        // add t option
        options.addOption(new Option("h", "help", false, "Help"));
        options.addOption(new Option("i", "input", true, "Input directory"));
        options.addOption(new Option("o", "output", true, "Output directory"));
        options.addOption(new Option("f", "filters", true, "Image filters"));
        options.addOption(new Option("lf", "log-file", true, "Log file"));
        options.addOption(new Option("cf", "config-file", true, "Config file"));

        //Create the list that will contain the applied filters
        ArrayList<Filter> filtersList = new ArrayList<>();

        // Invokes the parser for the parameters
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Define the input directory
        App.logFilename = new String(cmd.getOptionValue("lf"));
        Logger.log("Log file filename: " + App.logFilename, true);

        //Define the input directory
        File inputDir = new File(cmd.getOptionValue("i"));
        Logger.log("Input directory: " + inputDir + "/", true);

        //Define the output directory
        File outputDir = new File(cmd.getOptionValue("o"));
        Logger.log("Output directory: " + outputDir + "/", true);

        // basic line print to space out the parameters information and the image treatment prints
        System.out.println();


        // parse filters
        String filterArg = cmd.getOptionValue("f"); // grayscale|blur

        String[] split = filterArg.split("\\|"); // blur, grayscale
        for (String s : split) {
            switch (s.charAt(0)) {
                case 'b': // blur
                    String[] splitBlur = s.split("\\:"); // split by : to get the argument
                    filtersList.add(new BlurFilter(Integer.valueOf(splitBlur[1])));
                    break;
                case 'g': // grayscale
                    filtersList.add(new GreyFilter());
                    break;
                case 'd': // dilate
                    String[] splitDilate = s.split("\\:"); // split by : to get the argument
                    filtersList.add(new DilateFilter(Integer.valueOf(splitDilate[1])));
                    break;
                case 't': // text
                    filtersList.add(new TextFilter("TEMP"));
                    break;
                default:
                    System.out.println(s + " is not a valid filter");
                    break;

            }
        }

        // File est dossier qui contient les images
        String directoryName = cmd.getOptionValue("i");
        File dir = new File(directoryName + "/");

        if (dir.isDirectory()) {
            for (File f : inputDir.listFiles()) {

                if (!f.getName().endsWith(".jpg")) {
                    continue;
                }

                BufferedImage img = null;
                String imageFilename = f.getName();

                // Read the image
                new Mat();
                Mat image = imread(f.getAbsolutePath());

                String msgAddingFilters = "Adding filters to: " + inputDir + "/" + f.getName() + "...";
                System.out.print("\u001B[35m");
                System.out.println(msgAddingFilters);
                System.out.print("\u001B[0m");

                Logger.log(msgAddingFilters, false);
                try {
                    for (Filter fi : filtersList) {
                        image = fi.process(image);
                    }

                } catch (FilterException e) {
                    Logger.log("An error occurred : file not found", true);
                    e.printStackTrace();
                }

                // Writing the image
                imwrite(outputDir + "/" + imageFilename, image);
                //Logging the sucess
                System.out.print("\u001B[32m");
                Logger.log("SUCCESS" + " - Output image: " + "output/" + imageFilename + "\n", true);
                System.out.print("\u001B[0m");


            }
        }


    }

}