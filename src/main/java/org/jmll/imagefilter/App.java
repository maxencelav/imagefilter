package org.jmll.imagefilter;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Kernel;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_imgproc.CvFont;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;

import java.awt.*;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class App {

    public static String logFilename = "image.log";

    // File est dossier qui contient les images
    static String directoryName = "imgs";
    static final File dir = new File(directoryName + "/");

    // array des extensions supportées
    static final String[] EXTENSIONS = new String[]{
            "png", "bmp", "jpg", "jpeg" // and other formats you need
    };

    // filter to identify images based on their extensions
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

        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {

                BufferedImage img = null;

                String imageFilename = f.getName();

                // Read the image
                new Mat();
                Mat image = imread(f.getAbsolutePath());
                //

                if (image != null) {

                    // Blur
                    GaussianBlur(image, image, new Size(11, 11), 0);

                    // N&B
                    cvtColor(image, image, CV_RGB2GRAY);

                    // Dilatation
                    Mat kernel;
                    kernel = Mat.ones(40, 35, 2).asMat();
                    dilate(image, image, kernel);

                    // Add team name (bonus)
                    // Imgproc.putText(image, "Centeam", new Point(50, 50), CV_FONT_HERSHEY_PLAIN, 3, new Scalar());

                    // Writing the image
                    imwrite("output/"+imageFilename, image);
                    System.out.println("Filter added!");

                }


            }
        }


    }

}