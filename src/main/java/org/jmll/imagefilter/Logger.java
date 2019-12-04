package org.jmll.imagefilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class Logger {

    static void log(String logFilename,String message) {
        try {
            FileWriter myLog = new FileWriter(logFilename, true);
            Timestamp ts = new Timestamp(new Date().getTime());
            myLog.write(ts + " | " + message + "\n");
            myLog.close();
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    static void dumpLog(String logFilename) {

        try {
            File f = new File(logFilename);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
