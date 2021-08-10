package com.example.javacv.controller;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractController {
 
    protected static final String ERROR_WHEN_TRY_PROCESS_IAMGE = "Unexpected error occurred when try process your image!";

    protected static String extension(MultipartFile file) {

        var fileName = file.getOriginalFilename();
        if (fileName == null) return null;
		return fileName.split("\\.")[1];
	
    }

    protected ResponseEntity<InputStreamResource> downloadFile(byte[] blob, String fileName, String fileContentType) {

        var headers       = downloadFileHeards(fileName);
        var blobStream    = new ByteArrayInputStream(blob);
        var resourceSteam = new InputStreamResource(blobStream);
        
        return ResponseEntity.ok()
            .contentLength(blob.length)
            .headers(headers)
            .contentType(MediaType.valueOf(fileContentType))
            .body(resourceSteam);
    }

    protected ResponseEntity<String> internalServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ERROR_WHEN_TRY_PROCESS_IAMGE);
    }

    protected HttpHeaders downloadFileHeards(String fileName) {
        var header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=".concat(fileName));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;
    }
}
