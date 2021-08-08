package com.example.javacv.util;

import java.nio.file.Paths;

public class PathsUtil {
    
    private PathsUtil() {
        // only static methods
    }

    public static String getAbsolutePath(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }
}
