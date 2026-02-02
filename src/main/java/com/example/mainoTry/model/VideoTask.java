package com.example.mainoTry.model;

import lombok.Getter;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Getter
@Setter
public class VideoTask {
    private String title;
    private String description;
    private List<String> tags;
    private String videoUrl;
    private String privacyStatus;

    private boolean success;
    private String errorMessage;
    private String youtubeVideoId;

    public void validate() {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is missing or empty.");
        }

        if (videoUrl == null || videoUrl.isEmpty()) {
            throw new IllegalArgumentException("Video URL is missing or empty.");
        }

        if (!isValidUrl(videoUrl)) {
            throw new IllegalArgumentException("Video URL is invalid: " + videoUrl);
        }

        if (privacyStatus == null || !List.of("public", "private", "unlisted").contains(privacyStatus.toLowerCase())) {
            throw new IllegalArgumentException("Invalid privacy status: " + privacyStatus);
        }
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);  // Check if URL is well-formed
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
