package com.example.mainoTry.controller;

import com.example.mainoTry.model.UploadJob;
import com.example.mainoTry.service.CsvParserService;
import com.example.mainoTry.service.JobService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final CsvParserService csvService;
    private final JobService jobService;

    public JobController(CsvParserService csvService, JobService jobService) {
        this.csvService = csvService;
        this.jobService = jobService;
    }

    @PostMapping
    public String upload(@RequestParam MultipartFile file) throws Exception {
        var tasks = csvService.parse(file.getInputStream());
        return jobService.createJob(tasks);
    }

    @GetMapping("/{jobId}")
    public UploadJob status(@PathVariable String jobId) {
        return jobService.getJob(jobId);
    }
}
