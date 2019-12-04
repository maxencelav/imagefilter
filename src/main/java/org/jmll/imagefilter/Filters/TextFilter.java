package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.jmll.imagefilter.Logger;

import org.opencv.imgproc.Imgproc;

import static org.bytedeco.opencv.global.opencv_imgproc.CV_FONT_HERSHEY_PLAIN;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;

public class TextFilter extends Filter {

    String text;

    public TextFilter(String text) {
        this.text = text;
    }

    public Mat process(Mat image) throws FilterException {
        Mat cloneImage = image.clone();
        try {
            putText(image, this.text, new Point(50, 50), CV_FONT_HERSHEY_PLAIN, 3, new Scalar(255));
        } catch (Exception e) {

            e.printStackTrace();
        }
        Logger.log("Adding text filter", true);
        return cloneImage;
    }

}
