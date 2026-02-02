package com.example.mainoTry.service;

import com.example.mainoTry.model.VideoTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;

import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URL;
import java.util.*;

@Slf4j
@Service
public class CsvParserService {

    public List<VideoTask> parse(InputStream csvInput) throws IOException {
        List<VideoTask> tasks = new ArrayList<>();

        CSVParser parser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(csvInput));

        for (CSVRecord record : parser) {
            VideoTask task = new VideoTask();
            try {
                task.setTitle(record.get("title"));
                task.setDescription(record.get("description"));
                task.setVideoUrl(record.get("video_url"));
                task.setPrivacyStatus(record.get("privacy_status"));
                // Parse comma-separated tags
                String rawTags = record.get("tags");
                task.setTags(Arrays.stream(rawTags.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList());
                tasks.add(task);
            } catch (Exception e) {
                log.error("Invalid record: {}. Error: {}", record, e.getMessage());
            }
        }
        return tasks;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
