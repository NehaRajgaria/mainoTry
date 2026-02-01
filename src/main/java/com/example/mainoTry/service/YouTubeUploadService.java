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
            File videoFile = VideoDownloader.download(task.videoUrl);

            task.youtubeVideoId = youTubeClient.upload(
                    videoFile,
                    task.title,
                    task.description,
                    task.privacyStatus,
                    task.tags
            );
            task.success = true;

        } catch (Exception e) {
            task.success = false;
            task.errorMessage = e.getMessage();
        }
    }
}

