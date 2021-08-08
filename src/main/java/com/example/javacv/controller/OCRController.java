package com.example.javacv.controller;

import com.example.javacv.service.OCRService;
import com.example.javacv.service.OpenCvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("ocr")
@SuppressWarnings("java:S1452")
public class OCRController {

    private static final String ERROR_WHEN_TRY_PROCESS_IAMGE = "Unexpected error occurred when try process your image!";

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

    private ResponseEntity<String> internalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ERROR_WHEN_TRY_PROCESS_IAMGE);
    }
}
