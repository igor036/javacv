package com.example.javacv.enumerator;

import java.awt.image.BufferedImage;

public enum BufferedImageType {
    
    RGB(BufferedImage.TYPE_3BYTE_BGR),
    GRAY(BufferedImage.TYPE_BYTE_GRAY);

    public final int flag;    

    BufferedImageType(int flag) {
        this.flag = flag;
    }

}
