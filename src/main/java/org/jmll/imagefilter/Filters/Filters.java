package org.jmll.imagefilter.Filters;

import javafx.scene.effect.GaussianBlur;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_core.Mat;
import org.jmll.imagefilter.FiltersException;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Filters {

    public Filters {

    }

    public Mat toBlur (Mat image) throws FiltersException {
        try {
            int size = 3;
            Mat cloneImage = image.clone();
            opencv_imgproc.GaussianBlur(image, cloneImage, new Size(size, size), 0);

        } catch {

        }   return cloneImage;

    }
}
