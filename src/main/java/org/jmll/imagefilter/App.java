package org.jmll.imagefilter;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


public class App {

    public static void main(String[] args) {

        Mat image = imread("imgs/test_image.png");
        if (image != null) {
            //GaussianBlur(image, image, new Size(3, 3), 0);
            cvtColor(image,"output/gray_image.png", CV_BGR2GRAY);
            imwrite("output/test_image.png", image);
        }

    }

}