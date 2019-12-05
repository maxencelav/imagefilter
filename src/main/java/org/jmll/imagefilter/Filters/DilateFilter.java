package org.jmll.imagefilter.Filters;

import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.jmll.imagefilter.Logger;
import org.opencv.imgproc.Imgproc;

public class DilateFilter extends Filter {

    int size;

    /**
     * Function which allow to dilate the picture
     *
     * @param size : size of the picture, can't be pair
     */
    public DilateFilter(int size) {
        this.size = size;
    }

    /**
     * @param image : picture in the folder
     * @return a clone of the picture with the filter
     * @throws FilterException if the size is pair, throw the exception
     */
    @Override
    public Mat process(Mat image) throws FilterException {
        Mat cloneImage = image.clone();
        try {
            Mat element = opencv_imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * size + 1, 2 * size + 1));
            opencv_imgproc.dilate(image, cloneImage, element);
            Logger.log("Adding dilate filter with size = " + size, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return cloneImage;
    }
}

