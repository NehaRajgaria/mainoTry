package com.example.mainoTry.controller;

import com.example.mainoTry.model.Report;
import com.example.mainoTry.model.UploadJob;
import com.example.mainoTry.service.CsvParserService;
import com.example.mainoTry.service.JobService;
import com.example.mainoTry.service.ReportService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final CsvParserService csvService;
    private final JobService jobService;
    private final ReportService reportService;

    public JobController(CsvParserService csvService, JobService jobService, ReportService reportService) {
        this.csvService = csvService;
        this.jobService = jobService;
        this.reportService = reportService;
    }

    @PostMapping
    public String upload(@RequestParam MultipartFile file) throws Exception {
        // Check if file is not empty
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }
        // Enforce .csv MIME type
        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new IllegalArgumentException("Invalid file format. Please upload a CSV file.");
        }
        var tasks = csvService.parse(file.getInputStream());
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("CSV file contains no valid video records.");
        }
        return jobService.createJob(tasks);
    }

    @GetMapping("/{jobId}")
    public UploadJob status(@PathVariable String jobId) {
        return jobService.getJob(jobId);
    }

    @GetMapping("/{jobId}/report")
    public Report getReport(@PathVariable String jobId) {
        UploadJob job = jobService.getJob(jobId);
        if (job == null) {
            throw new IllegalArgumentException("Job not found: " + jobId);
        }
        return reportService.generateReport(job);
    }
}
