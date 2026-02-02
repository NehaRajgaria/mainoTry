package com.example.mainoTry.service;

import com.example.mainoTry.enums.JobStatus;
import com.example.mainoTry.model.UploadJob;
import com.example.mainoTry.model.VideoTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
public class JobService {

    private final Map<String, UploadJob> jobs = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final YouTubeUploadService uploadService;

    public JobService(YouTubeUploadService uploadService) {
        this.uploadService = uploadService;
    }

    public String createJob(List<VideoTask> tasks) {
        UploadJob job = new UploadJob();
        job.videos = tasks;
        jobs.put(job.jobId, job);

        executor.submit(() -> process(job));
        return job.jobId;
    }

    private void process(UploadJob job) {
        log.info("Job {} processing started with {} videos.", job.jobId, job.videos.size());
        job.status = JobStatus.IN_PROGRESS;

        for (VideoTask task : job.videos) {
            int retryLimit = 3, retries = 0;
            while (retries < retryLimit) {
                uploadService.upload(task);
                if (task.success) {
                    log.info("Video {} uploaded successfully.", task.title);
                    break;
                }
                retries++;
                log.info("Error processing video {} in try number {}.", task.title,  retries);
            }
        }

        job.status = JobStatus.COMPLETED;
    }

    public UploadJob getJob(String jobId) {
        return jobs.get(jobId);
    }
}

