package com.example.javacv.service;

import java.awt.image.BufferedImage;

import com.example.javacv.exception.CannotRecognizeTextException;
import com.example.javacv.util.PathsUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TesseractService {
       
    private final String datapath;
    private final String language;
    private final Tesseract tesseract;

    public TesseractService(
        @Value("${tesseract.datapath}") String datapath, 
        @Value("${tesseract.language}") String language) {

        tesseract     = new Tesseract();
        this.language = language;
        this.datapath = PathsUtil.getAbsolutePath(datapath);
        
        loadDataSource();
    }

    private void loadDataSource() {
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
    }

    public String recognize(BufferedImage image) {
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException ex) {
            throw new CannotRecognizeTextException(ex);
        }
    }
}
