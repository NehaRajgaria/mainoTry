package com.example.mainoTry.service;

import com.example.mainoTry.model.Report;
import com.example.mainoTry.model.UploadJob;
import com.example.mainoTry.model.VideoTask;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    public Report generateReport(UploadJob job) {
        // Count statistics
        int totalVideos = job.getVideos().size();
        List<VideoTask> successfulTasks = job.getVideos().stream()
                .filter(VideoTask::isSuccess)
                .toList();
        List<VideoTask> failedTasks = job.getVideos().stream()
                .filter(task -> !task.isSuccess())
                .toList();

        // Populate the report
        Report report = new Report();
        report.setJobId(job.getJobId());
        report.setStatus(job.getStatus().toString());
        report.setTotalVideos(totalVideos);
        report.setSuccessfulUploads(successfulTasks.size());
        report.setFailedUploads(failedTasks.size());

        report.setTasks(job.getVideos()); // Include both successful and failed tasks

        return report;
    }
}