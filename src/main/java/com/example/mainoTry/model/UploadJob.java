package com.example.mainoTry.model;

import com.example.mainoTry.enums.JobStatus;

import java.util.List;
import java.util.UUID;

public class UploadJob {
    public String jobId = UUID.randomUUID().toString();
    public JobStatus status = JobStatus.PENDING;
    public List<VideoTask> videos;
}

