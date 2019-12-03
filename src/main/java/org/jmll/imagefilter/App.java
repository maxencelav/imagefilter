package org.jmll.imagefilter;

import javafx.css.FontFace;
import javafx.scene.effect.GaussianBlur;
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


public class App {

    public static void main(String[] args) {

        // Read the image

        new Mat();
        Mat image = imread("imgs/test_image.png");
        //
        if (image != null) {

            // Blur
            GaussianBlur(image, image, new Size(3, 3), 0);

           // N&B
           cvtColor(image, image, CV_RGB2GRAY);

            // Dilatation
           Mat kernel;
           kernel = Mat.ones(40,35, 2).asMat();
           dilate(image, image, kernel);

            // Add team name (bonus)
           // Imgproc.putText(image, "Centeam", new Point(50, 50), CV_FONT_HERSHEY_PLAIN, 3, new Scalar());

            // Writing the image
            imwrite("output/test_image.png", image);
            System.out.println("Filter added!");
        }

    }

}