package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.jmll.imagefilter.Logger;

public class BlurFilter extends Filter {

    int size;

    public BlurFilter(int size) {
        this.size = size;
    }

    public Mat process(Mat image) throws FilterException {
        // TODO : faire un try catch pour envoyer une exception qd size est pair
        if (this.size % 2 == 0) {
            throw new FilterException("An error occurred : Size not valid");
        }

        Mat cloneImage = image.clone();
        try {
            opencv_imgproc.GaussianBlur(image, cloneImage, new Size(size, size), 0);
        } catch (Exception e) { // A MODIFIER
            throw new FilterException("An error occurred");
        }

        Logger.log("Adding blur filter with size = " + size, true);

        return cloneImage;
    }
}
