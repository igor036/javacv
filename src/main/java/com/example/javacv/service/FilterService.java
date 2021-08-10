package com.example.javacv.service;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    
    @Autowired
    private OpenCvService openCvService; 


    public Mat grayScaleMagicColor(Mat image, double minHueValue, double maxHueValue) {

        var gray  = openCvService.grayScale2BGR(openCvService.grayScale(image));
		var hsv   = openCvService.hsv(image);

        for (var x = 0; x < hsv.rows(); x++) {
			for (var y = 0; y < hsv.cols(); y++) { 

				var hsvPixel = hsv.get(x, y);

				if (hsvPixel[0] >= minHueValue && hsvPixel[0] <= maxHueValue) {
					gray.put(x, y, image.get(x, y));
				}
			}
		}

        return gray;
    }
}
