package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.FiltersException;
import org.opencv.core.CvType;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Filters {

    /*public Filters{

    }*/

    public Mat toBlur(Mat image, int size) {

        // TODO : faire un try catch pour envoyer une exception qd size est pair

        Mat cloneImage = image.clone();
        opencv_imgproc.GaussianBlur(image, cloneImage, new Size(size, size), 0);

        return cloneImage;

    }

    public Mat toGrayScale(Mat image) {

        Mat cloneImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC3);
        opencv_imgproc.cvtColor(image, cloneImage, Imgproc.COLOR_RGB2GRAY);

        return cloneImage;
    }

    public Mat toDilate(Mat image, int size) {
        Mat cloneImage = image.clone();
        Mat element = opencv_imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * size + 1, 2 * size + 1));
        opencv_imgproc.dilate(image, cloneImage, element);
        return cloneImage;
    }

    // A finir - bonus
    public Mat toText (Mat image) {
        // Add team name (bonus)
        // Imgproc.putText(image, "Centeam", new Point(50, 50), CV_FONT_HERSHEY_PLAIN, 3, new Scalar());
        return;
    }
}
