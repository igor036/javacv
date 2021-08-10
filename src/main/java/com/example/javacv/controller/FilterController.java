package com.example.javacv.controller;

import java.io.IOException;

import com.example.javacv.service.FilterService;
import com.example.javacv.service.OpenCvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("filter")
@SuppressWarnings("java:S1452")
public class FilterController extends AbstractController {
    
    @Autowired
	private OpenCvService openCvService;

    @Autowired
    private FilterService filterService;

    @GetMapping("gray/magicolor")
    public ResponseEntity<?> grayScaleMagicColor(@RequestPart MultipartFile file, @RequestParam double minHueValue, @RequestParam double maxHueValue) {

        try {

            var fileContentType = file.getContentType();
            var fileExtension   = extension(file);
            var fileName        = file.getOriginalFilename();
            
            var image = openCvService.imageRead(file.getBytes());
            var gray  = filterService.grayScaleMagicColor(image, minHueValue, maxHueValue);
            var blob  = openCvService.blob(gray, fileExtension);

            return downloadFile(blob, fileName, fileContentType);

        } catch (IOException e) {
           return internalServerError();
        }
    }
}
