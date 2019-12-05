package org.jmll.imagefilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Logger {


    /**
     * Function which allow to print the message in a log or/and the terminal
     * @param message : logged message
     * @param consolePrint : printed to the terminal if true
     */
    public static void log(String message, boolean consolePrint) {
        try {
            FileWriter myLog = new FileWriter(App.logFilename, true);

            //FileWriter myLog = new FileWriter("image.log", true);
            Timestamp ts = new Timestamp(new Date().getTime());
            myLog.write(ts + " | " + message + "\n");
            if (consolePrint) {
                System.out.println(message);
            }
            myLog.close();
        } catch (IOException e) {
            System.out.println("\u001B[31m" + "An error while logging occurred." + "\u001B[0m");
            e.printStackTrace();
        }
    }

    static void dumpLog() {

        try {
            File f = new File(App.logFilename);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31m" + "An error while dumping the log file occurred." + "\u001B[0m");
            e.printStackTrace();
        }

    }

}
