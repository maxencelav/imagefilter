package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.jmll.imagefilter.Logger;


public class BlurFilter extends Filter {

    int size;

    /**
     * Function which allow to blur the picture
     * @param size : size of the picture, can't be pair
     */
    public BlurFilter(int size) {
        this.size = size;
    }

    /**
     *
     * @param image : picture in the folder
     * @return a clone of the picture with the filter
     * @throws FilterException if size is pair throw the exception
     */
    public Mat process(Mat image) throws FilterException {

        // check if the size is pair or not
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
