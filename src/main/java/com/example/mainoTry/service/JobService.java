package com.example.mainoTry.service;

import com.example.mainoTry.enums.JobStatus;
import com.example.mainoTry.model.UploadJob;
import com.example.mainoTry.model.VideoTask;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;

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
        job.status = JobStatus.IN_PROGRESS;

        for (VideoTask task : job.videos) {
            int retryLimit = 3, retries = 0;
            while (retries < retryLimit) {
                uploadService.upload(task);
                if (task.success) break;
                retries++;
            }
        }

        job.status = JobStatus.COMPLETED;
    }

    public UploadJob getJob(String jobId) {
        return jobs.get(jobId);
    }
}

