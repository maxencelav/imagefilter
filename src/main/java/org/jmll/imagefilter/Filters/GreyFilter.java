package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.Logger;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

public class GreyFilter extends Filter {

    /**
     * Function which allow to add a grey filter
     *
     * @param image picture in the folder
     * @return a clone of the picture with the filter
     * @throws FilterException if the filter has encountered an exception
     */

    public Mat process(Mat image) throws FilterException {
        Mat cloneImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC3);

        try {
            opencv_imgproc.cvtColor(image, cloneImage, Imgproc.COLOR_RGB2GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.log("Adding grayscale filter", true);
        return cloneImage;
    }
}
