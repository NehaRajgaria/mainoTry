package com.example.mainoTry.service;

import com.example.mainoTry.model.VideoTask;
import org.apache.commons.csv.*;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class CsvParserService {

    public List<VideoTask> parse(InputStream csvInput) throws IOException {
        List<VideoTask> tasks = new ArrayList<>();

        CSVParser parser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(csvInput));

        for (CSVRecord record : parser) {
            VideoTask task = new VideoTask();
            task.title = record.get("title");
            task.description = record.get("description");
            task.videoUrl = record.get("video_url");
            task.privacyStatus = record.get("privacy_status");
            // ✅ Parse comma-separated tags
            String rawTags = record.get("tags");
            task.tags = Arrays.stream(rawTags.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            tasks.add(task);
        }
        return tasks;
    }
}
