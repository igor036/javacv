package com.example.javacv.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import nu.pattern.OpenCV;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.javacv.enumerator.BufferedImageType;

@Service
public class OpenCvService {

    private static final String DEFAULT_WINDOW_TITLE = "Image";
    private static final double THRESHOLD_MAX_VALUE  = 255d;

    @PostConstruct
    public void loadNativeOpenCv() {
        OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.setProperty("java.awt.headless", "false");
    }

    public Mat imageRead(String path) {
        return Imgcodecs.imread(path);
    }

    public Mat imageRead(byte[] blobImage) {
		return Imgcodecs.imdecode(
            new MatOfByte(blobImage), 
            Imgcodecs.IMREAD_UNCHANGED
        );
	}

    public void show(Mat image) {
        show(DEFAULT_WINDOW_TITLE, image);
    }

    public void show(String windowTitle, Mat image) {
        HighGui.imshow(windowTitle, image);
        HighGui.waitKey();
    }

    public Mat grayScale(Mat image) {

        if (image.channels() == 1) {
            return image;
        }

        var gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
        return gray;
    }

    public Mat grayScale2BGR(Mat image) {

        if (image.channels() > 1) {
            return image;
        }

        var rgb = new Mat();
        Imgproc.cvtColor(image, rgb, Imgproc.COLOR_GRAY2BGR);
        return rgb;
    }

    public Mat hsv(Mat image) {
        var hsv = new Mat();
		Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
        return hsv;
    }

    public Mat gaussianBlur(Mat image, int size) {
        var gaussian = new Mat();
        Imgproc.GaussianBlur(image, gaussian, new Size(size, size), 0);
        return gaussian;
    }


    public Mat adaptiveThreshold(Mat image, int blockSize, double c) {
        var threshold = new Mat();
        Imgproc.adaptiveThreshold(image, threshold, THRESHOLD_MAX_VALUE, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, blockSize, c); 
        return threshold;
    }


    public Mat copy(Mat image) {
        Mat copy = new Mat();
        image.copyTo(copy);
        return copy;
    }

    public byte[] blob(Mat image, String extension) {
		var bytes = new MatOfByte();
		Imgcodecs.imencode(".".concat(extension), image, bytes);
		return bytes.toArray();
	}

    public List<MatOfPoint> findContours(Mat image) {
        var contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    public Rect contour2Rect(MatOfPoint contour) {
        return Imgproc.boundingRect(contour);
    }

    public BufferedImage bufferedImage(Mat image, BufferedImageType imageType) {

        var bufferSize = image.channels() * image.cols() * image.rows();
        var buffer     = new byte[bufferSize];
        
        image.get(0, 0, buffer);

        var bufferedImage = new BufferedImage(image.cols(), image.rows(), imageType.flag);
        var targetPixels  = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        return bufferedImage;
    }
}
