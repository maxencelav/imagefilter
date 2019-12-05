package org.jmll.imagefilter;

import org.apache.commons.cli.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.ini4j.Wini;
import org.jmll.imagefilter.Filters.*;
import org.reflections.Reflections;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;


public class App {

    //Define the log file
    public static String logFilename;


    public static void main(String[] args) {

        // print given arguments to console
        System.out.println("Current arguments: " + Arrays.toString(args));
        System.out.println();

        // create Options object
        Options options = new Options();


        // add t option
        options.addOption(new Option("h", "help", false, "Help"));
        options.addOption(new Option("i", "input", true, "Input directory"));
        options.addOption(new Option("o", "output", true, "Output directory"));
        options.addOption(new Option("f", "filters", true, "Image filters"));
        options.addOption(new Option("lf", "log-file", true, "Log file"));
        options.addOption(new Option("cf", "config-file", true, "Config file"));

        // Story 12
        // options.addOption(new Option("fl", "list-filters", true, "Filters list"))   ;

        //Create the list that will contain the applied filters
        ArrayList<Filter> filtersList = new ArrayList<>();

        //Create the default values for the arguments
        File inputDir = new File("imgs");
        App.logFilename = "image.log";
        File outputDir = new File("output");
        String filters = null;


        // Invokes the parser for the parameters
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // FROM INI FILE
        System.out.println("Reading from the ini file...");

        // Opening .ini file
        Wini ini = null;
        try {
            ini = new Wini(new File(cmd.getOptionValue("cf")));

            //Define the log file filename
            try {
                App.logFilename = new String(ini.get("general", "logFile"));
                Logger.log("Log file filename: " + App.logFilename, true);
            } catch (NullPointerException e) {
                System.out.println("Log file filename not found. Proceeding with default name: image.log");
                e.printStackTrace();
            }

            //Define the input directory
            try {
                inputDir = new File(ini.get("general", "inputDir"));
                Logger.log("Input directory: " + inputDir + "/", true);
            } catch (NullPointerException e) {
                System.out.println("Input directory not found. Proceeding with default name: imgs/");
                e.printStackTrace();
            }

            //Define the output directory
            try {
                outputDir = new File(ini.get("general", "outputDir"));
                Logger.log("Output directory: " + outputDir + "/", true);

            } catch (NullPointerException e) {
                System.out.println("Output directory not found. Proceeding with default name: output/");
                e.printStackTrace();
            }

            //Define the filters
            try {
                filters = new String(ini.get("filters", "content"));
                Logger.log("Filters: " + filters, true);

            } catch (NullPointerException e) {
                System.out.println("Filters not found.");
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            System.out.println(".ini file not found.");
        } catch (IOException e) {
            System.out.println(".ini file not found.");
        }

        // basic line print to space out the ini file information and the parameters
        System.out.println();

        // FROM PARAMETERS
        Logger.log("Reading from the Java parameters...", true);

        //Define the log file filename
        try {
            App.logFilename = new String(cmd.getOptionValue("lf"));
            Logger.log("Log file file name: " + App.logFilename, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Define the input directory
        try {
            inputDir = new File(cmd.getOptionValue("i"));
            Logger.log("Input directory: " + inputDir + "/", true);
        } catch (NullPointerException e) {
            System.out.println("Input directory not found. Proceeding with: " + inputDir + "/");
        }

        //Define the output directory
        try {
            outputDir = new File(cmd.getOptionValue("o"));
            Logger.log("Output directory: " + outputDir + "/", true);
        } catch (NullPointerException e) {
            System.out.println("Output directory not found. Proceeding with: " + outputDir + "/");
        }

        try {
            filters = new String(cmd.getOptionValue("f"));
            Logger.log("Filters: " + filters, true);

        } catch (NullPointerException e) {
            System.out.println("Filters not found.");
        }

        // basic line print to space out the parameters information and the image treatment prints
        System.out.println();

        // parse filters
        String filterArg = filters;

        String[] split = filterArg.split("\\|");
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
                    String[] textDilate = s.split("\\:"); // split by : to get the argument
                    filtersList.add(new TextFilter(String.valueOf(textDilate[1])));
                    break;
                default:
                    System.out.println(s + " is not a valid filter");
                    break;

            }
        }

        // File est dossier qui contient les images
        String directoryName = inputDir.toString();
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

        // Story 12 (bonus)


        Class<App> main = App.class;
        Class<BlurFilter> blurClassObj = BlurFilter.class;
        Class<DilateFilter> dilateClassObj = DilateFilter.class;
        Method[] filterMethod = blurClassObj.getDeclaredMethods();
        System.out.println("Name of the class : " + blurClassObj.getName());
        Package pkg = main.getPackage();
        System.out.println("Package Name = " + pkg.getName());
        for (Method method : filterMethod) {
            System.out.println("Name of the method : " + method.getName());
        }

    }

}