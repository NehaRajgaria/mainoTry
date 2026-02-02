package com.example.mainoTry.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Report {
    private String jobId;
    private String status;
    private int totalVideos;
    private int successfulUploads;
    private int failedUploads;
    private List<VideoTask> tasks;
}