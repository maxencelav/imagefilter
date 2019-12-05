package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.jmll.imagefilter.Logger;

import org.opencv.imgproc.Imgproc;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class TextFilter extends Filter {

    String text;

    /**
     * Function which allow to display a text on the picture
     * @param text : text on the picture
     */
    public TextFilter(String text) {
        this.text = text;
    }

    /**
     * Function mat which configure the text's position and the text's font
     * @param image picture in the folder
     * @return a clone of the picture with the filter
     * @throws FilterException if the filter has encountered an exception
     */
    public Mat process(Mat image) throws FilterException {
        Mat cloneImage = image.clone();
        try {
          putText(image, this.text, new Point(150, 150), CV_FONT_HERSHEY_SIMPLEX, 1.2, new Scalar(0xff));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.log("Adding text filter with text = "+text, true);
        return cloneImage;
    }

}
