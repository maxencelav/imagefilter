package org.jmll.imagefilter;

import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.Filters.Filter;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

public class App {

    public static String logFilename = "image.log";

    // File est dossier qui contient les images
    static String directoryName = "imgs";
    static final File dir = new File(directoryName + "/");

    // array des extensions supportées
    static final String[] EXTENSIONS = new String[]{
            "png", "bmp", "jpg", "jpeg"
    };

    // filtre les images selon les extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };


    public static void main(String[] args) {

        if (dir.isDirectory()) {
            for (final File f : dir.listFiles(IMAGE_FILTER)) {

                BufferedImage img = null;

                String imageFilename = f.getName();

                // Read the image
                new Mat();
                Mat image = imread(f.getAbsolutePath());

                String msgAddingFilters = "Adding filters to: " + f.getAbsolutePath() ;
                System.out.print(msgAddingFilters + " => ");
                Logger.log(msgAddingFilters,false);
                //

                try {
                    Filter.toGrayScale(image);
                    Filter.toBlur(image,5);
                    Filter.toDilate(image,8);
                }
                catch(FileNotFoundException e){
                    Logger.log("An error occurred : file not found",true);
                    e.printStackTrace();
                }
                
                    // Writing the image
                    imwrite("output/"+imageFilename, image);
                    Logger.log("\u001B[32m"+"SUCESS\n"+"\u001B[0m"+
                            "Output image: "+"output/"+imageFilename,true);


            }
        }


    }

}