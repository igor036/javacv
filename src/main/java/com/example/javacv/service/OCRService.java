package com.example.javacv.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.javacv.enumerator.BufferedImageType;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OCRService {
    
    @Autowired
	private OpenCvService openCvService;

	@Autowired
	private TesseractService tesseractService;

    public String recognizePlate(Mat image) {

        var resized = openCvService.resize(image, 620, 480);
        var gray    = openCvService.grayScale(resized);
		var filter  = openCvService.bilateralFilter(gray, 13, 15, 15);
		var canny   = openCvService.canny(filter, 30, 200);

       var plate = openCvService.findContours(canny).stream()
			.sorted(openCvService::compare)
			.filter(openCvService::isRectangle).limit(10)
			.map(openCvService::contour2Rect)
			.findFirst();

        if (plate.isPresent()) {
            openCvService.show(resized.submat(plate.get()));
            return recognize(resized.submat(plate.get()));
        }

        return "";
    }


    public String recognize(Mat image) {

        var processedImage = preProcess(image, 3, 15, 2);
        var bufferedImage  = openCvService.bufferedImage(processedImage, BufferedImageType.GRAY);
        
        return tesseractService.recognize(bufferedImage);
    }

    public List<String> recognizeParagraphs(Mat image) {

        var processedImage = preProcess(image, 67, 255, 2);
        var contours       = openCvService.findContours(processedImage);

        return contours.stream()
            .map(contour -> recognize(contour, image))
            .filter(StringUtils::hasText)
            .collect(Collectors.toList());
    }


    private String recognize(MatOfPoint contour, Mat image) {
        
        var rect            = openCvService.contour2Rect(contour);
        var region          = image.submat(rect);
        var processedImage  = preProcess(region, 3, 15, 2);
        var recoginizedText = recognize(processedImage);

        return recoginizedText
            .replace("\n", "")
            .replace("\r", "");
    }


    public Mat preProcess(Mat image, int gaussianBlurSize, int thresholdBlockSize, double thresholdC) {
        var gray      = openCvService.grayScale(image);
        var gaussian  = openCvService.gaussianBlur(gray, gaussianBlurSize);
        return openCvService.adaptiveThreshold(gaussian, thresholdBlockSize, thresholdC);
    }


}
