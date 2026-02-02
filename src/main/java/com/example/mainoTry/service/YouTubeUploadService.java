package com.example.mainoTry.service;

import com.example.mainoTry.model.VideoTask;
import com.example.mainoTry.util.VideoDownloader;
import com.example.mainoTry.util.YouTubeClient;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class YouTubeUploadService {

    private final YouTubeClient youTubeClient;

    public YouTubeUploadService() throws GeneralSecurityException, IOException {
        this.youTubeClient = new YouTubeClient();
    }

    public void upload(VideoTask task) {
        try {
            // Validate task parameters
            task.validate();
            File videoFile = VideoDownloader.download(task.getVideoUrl());

            task.setYoutubeVideoId(youTubeClient.upload(
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
}

