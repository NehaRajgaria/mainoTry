package com.example.mainoTry.service;

import com.example.mainoTry.model.UploadJob;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    public String generateReport(UploadJob job) {
        List<String> successList = job.videos.stream()
                .filter(task -> task.success)
                .map(task -> "Video: " + task.title + " - Status: Success")
                .collect(Collectors.toList());

        List<String> failureList = job.videos.stream()
                .filter(task -> !task.success)
                .map(task -> "Video: " + task.title + " - Status: Failed - Error: " + task.errorMessage)
                .collect(Collectors.toList());

        return "Report for Job ID: " + job.jobId + "\n"
                + "Total Videos: " + job.videos.size() + "\n"
                + "Success: " + successList.size() + "\n"
                + "Failed: " + failureList.size() + "\n\n"
                + "Details:\n" + String.join("\n", successList) + "\n" + String.join("\n", failureList);
    }
}