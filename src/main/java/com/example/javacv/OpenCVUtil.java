package com.example.javacv;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import nu.pattern.OpenCV;

public class OpenCVUtil {

    private static final String DEFAULT_WINDOW_TITLE = "Image";

    private OpenCVUtil() {
        // only static methods
    }

    public static void loadNativeOpenCv() {
        OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.setProperty("java.awt.headless", "false");
    }

    public static Mat imageRead(String path) {
        return Imgcodecs.imread(path);
    }

    public static void show(Mat image) {
        show(DEFAULT_WINDOW_TITLE, image);
    }

    public static void show(String windowTitle, Mat image) {
        HighGui.imshow(windowTitle, image);
        HighGui.waitKey();
    }

    public static Mat grayScale(Mat image) {
        var grayScale = copy(image);
        Imgproc.cvtColor(image, grayScale, Imgproc.COLOR_RGB2GRAY);
        return grayScale;
    }

    public static Mat copy(Mat image) {
        Mat copy = new Mat();
        image.copyTo(copy);
        return copy;
    }
}
