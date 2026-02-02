package com.example.mainoTry.service;

import com.example.mainoTry.model.VideoTask;
import com.example.mainoTry.util.VideoDownloader;
import com.example.mainoTry.util.YouTubeClient;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service
public class YouTubeUploadService {

    private static YouTube youtube;

    public YouTubeUploadService() throws GeneralSecurityException, IOException {
        youtube = YouTubeClient.getService();
    }

    public void upload(VideoTask task) {
        try {
            // Validate task parameters
            task.validate();
            log.info("Downloading video: {}", task.getTitle());
            File videoFile = VideoDownloader.download(task.getVideoUrl());
            log.info("Video downloaded: {}", task.getTitle());

            task.setYoutubeVideoId(uploadFile(
                    videoFile,
                    task.getTitle(),
                    task.getDescription(),
                    task.getPrivacyStatus(),
                    task.getTags()
            ));
            task.setSuccess(true);

        } catch (Exception e) {
            task.setSuccess(false);
            task.setErrorMessage(e.getMessage());
        }
    }

    private String uploadFile(File videoFile, String title, String description, String privacyStatus, List<String> tags) throws IOException {
        Video video = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setTags(tags);
        video.setSnippet(snippet);

        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(privacyStatus);
        video.setStatus(status);

        InputStreamContent content =
                new InputStreamContent("video/*", new FileInputStream(videoFile));

        Video response = youtube.videos()
                .insert("snippet,status", video, content)
                .execute();

        return response.getId();
    }
}

