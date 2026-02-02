package com.example.mainoTry.model;

import com.example.mainoTry.enums.JobStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UploadJob {
    private final String jobId = UUID.randomUUID().toString();
    private JobStatus status = JobStatus.PENDING;
    private List<VideoTask> videos;
}

