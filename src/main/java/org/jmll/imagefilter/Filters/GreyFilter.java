package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.Logger;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

public class GreyFilter extends Filter {

    public Mat toGrayScale(Mat image) {

        Mat cloneImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC3);
        opencv_imgproc.cvtColor(image, cloneImage, Imgproc.COLOR_RGB2GRAY);

        Logger.log("Adding grayscale filter",true);

        return cloneImage;
    }
}
