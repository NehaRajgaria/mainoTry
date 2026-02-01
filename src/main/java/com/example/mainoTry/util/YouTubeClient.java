package com.example.mainoTry.util;

import com.example.mainoTry.ApiExample;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YouTubeClient {

    // Reuse your ApiExample.getService()
    static YouTube youtube;

    public YouTubeClient() throws GeneralSecurityException, IOException {
        youtube = ApiExample.getService();
    }

    public String upload(File file, String title, String desc, String privacy, List<String> tags)
            throws Exception {

        Video video = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(desc);
        snippet.setTags(tags);
        video.setSnippet(snippet);

        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(privacy);
        video.setStatus(status);

        InputStreamContent content =
                new InputStreamContent("video/*", new FileInputStream(file));

        Video response = youtube.videos()
                .insert("snippet,status", video, content)
                .execute();

        return response.getId();
    }
}
