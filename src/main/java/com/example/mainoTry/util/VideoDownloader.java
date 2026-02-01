package com.example.mainoTry.util;

import java.io.*;
import java.net.URL;

public class VideoDownloader {

    public static File download(String url) throws IOException {
        File temp = File.createTempFile("video-", ".mp4");
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(temp)) {
            in.transferTo(out);
        }
        return temp;
    }
}

