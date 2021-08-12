package com.example.javacv.controller;

import com.example.javacv.service.OCRService;
import com.example.javacv.service.OpenCvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("ocr")
@SuppressWarnings("java:S1452")
public class OCRController extends AbstractController {

    @Autowired
    private OCRService oCRService;

    @Autowired
	private OpenCvService openCvService;

    @GetMapping("recognize")
    public ResponseEntity<String> recognize(@RequestPart MultipartFile file) {

        try {
            
            var image          = openCvService.imageRead(file.getBytes());
            var recognizedText = oCRService.recognize(image);

            return ResponseEntity.ok(recognizedText);

        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping("recognize/paragraphs")
    public ResponseEntity<?> recognizeParagraphs(@RequestPart MultipartFile file) {

        try {

            var image      = openCvService.imageRead(file.getBytes());
            var paragraphs = oCRService.recognizeParagraphs(image);

            return ResponseEntity.ok(paragraphs);

        } catch (Exception e) {
            return internalServerError();
        }
    }
    @GetMapping("recognize/plate")
    public ResponseEntity<?> recognizePlate(@RequestPart MultipartFile file) {

        try {

            var image = openCvService.imageRead(file.getBytes());
            var plate = oCRService.recognizePlate(image);

            return ResponseEntity.ok(plate);

        } catch (Exception e) {
            return internalServerError();
        }
    }
}
