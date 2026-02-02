package com.example.mainoTry.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class VideoDownloader {

    private static final long MAX_FILE_SIZE = 256L * 1024 * 1024 * 1024; // 256GB in bytes

    public static File download(String url) throws IOException {
        // Get the file size before downloading
        long fileSize = getFileSize(url);
        if (fileSize > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Video file exceeds the maximum size limit of 256GB.");
        }
        File temp = File.createTempFile("video-", ".mp4");
        try (InputStream in = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(temp)) {
            in.transferTo(out);
        } catch (IOException e) {
            if (temp.exists()) {
                temp.delete(); // Cleanup on failure to release disk space
            }
            throw e; // Bubble up the exception
        }
        return temp;
    }

    private static long getFileSize(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD"); // Use HEAD request to fetch file metadata
        connection.connect();

        long fileSize = connection.getContentLengthLong(); // Extract Content-Length header
        connection.disconnect();

        if (fileSize == -1) {
            log.warn("Unable to determine file size for URL: {}", fileUrl);
        }
        return fileSize;
    }
}

