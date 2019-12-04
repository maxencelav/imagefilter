package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.jmll.imagefilter.Logger;

public class BlurFilter extends Filter {

    private final int size;

    public BlurFilter (int size) {
        this.size = size;
    }

    public Mat toBlur(Mat image, int size) throws FilterException {

        // TODO : faire un try catch pour envoyer une exception qd size est pair
        if (size%2 != 0) {
            throw new FilterException ("An error occurred : Size not valid");
        }

        try {
            Mat cloneImage = image.clone();
            opencv_imgproc.GaussianBlur(image, cloneImage, new Size(size, size), 0);
        }
        catch (Exception e){ // A MODIFIER
            throw new FilterException("An error occurred : ");
        }

        Logger.log("Adding blur filter with size = "+size,true);
        return cloneImage;
    }
}
