package org.jmll.imagefilter.Filters;

import org.jmll.imagefilter.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import static org.bytedeco.opencv.global.opencv_imgproc.CV_FONT_HERSHEY_PLAIN;

public class TextFilter extends Filter {

    public Mat toText (Mat image) {

        try {
            Mat cloneImage = image.clone();
            Imgproc.putText(image, "Centeam", new Point(50, 50), CV_FONT_HERSHEY_PLAIN, 3, new Scalar(255));}
        catch (Exception e) {

            e.printStackTrace();
        }
        Logger.log("Adding text filter",true);
        return cloneImage;
    }*/
}
