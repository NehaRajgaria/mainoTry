# Bulk Video Upload System (Java / Spring Boot)

## Overview

This project implements a backend service designed to handle bulk video uploads to YouTube channels. Built using **Java Spring Boot**, the system efficiently processes CSV files containing video metadata and interacts with the **YouTube Data API v3** for video upload and metadata application.

---

### Sample CSV File Format for Job Submission API

Below is an example of the expected CSV schema for video metadata:

```csv
title,description,tags,video_url,privacy_status
"Product Demo","Tutorial video","demo,tutorial","https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4","unlisted"
"Joy Rides","About us","company,intro","https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4","public"
```

---

## How to Run the Service

### 1. Setup Java Environment
Ensure you have the following dependencies installed:
- JDK 17 or newer.
- Maven build tool.

### 2. Clone the Repository
```bash
git clone https://github.com/NehaRajgaria/mainoTry.git
cd mainoTry
```

### 3. Configure YouTube API Credentials
- Follow the **OAuth 2.0 setup guide** from [YouTube Data API v3 documentation](https://developers.google.com/identity/protocols/oauth2).
- Place the `client_secrets.json` file in the `src/main/resources` directory.

### 4. Build and Run the Application
To build and launch the Spring Boot application, run:
```bash
mvn spring-boot:run
```

### 5. Interact with the REST API
The server exposes the following REST API endpoints:

#### Submit a Job:
- Endpoint: `POST /jobs`
- Description: Accepts a CSV file and returns a job ID.
- Example cURL command:
  ```bash
  curl -X POST -F "file=@sample.csv" http://localhost:8080/jobs
  ```

#### Query Job Status:
- Endpoint: `GET /jobs/{jobId}`
- Description: Checks the current status of a specific job.

#### Generate Report:
- Endpoint: `GET /jobs/{jobId}/report`
- Description: Retrieves a detailed report for the specified job.

---

## System Design Highlights

### Architecture
- **Spring Boot** Framework: Simplifies the development of RESTful APIs.
- **Java Concurrency**: Ensures efficient processing of bulk jobs using a thread pool.
- **YouTube Data API v3 Integration**: Enables authenticated interactions with YouTube for file uploads.
- **Microservice Principles**: Decoupled services for extensibility and maintainability.

### Key Components

1. **CSV Parsing**:
    - [`CsvParserService`](https://github.com/NehaRajgaria/mainoTry/blob/main/src/main/java/com/example/mainoTry/service/CsvParserService.java): Validates and parses CSV input into a list of `VideoTask` objects.

2. **Job Management**:
    - [`JobService`](https://github.com/NehaRajgaria/mainoTry/blob/main/src/main/java/com/example/mainoTry/service/JobService.java): Handles job creation, tracking, and processing.

3. **REST Controller**:
    - [`JobController`](https://github.com/NehaRajgaria/mainoTry/blob/main/src/main/java/com/example/mainoTry/controller/JobController.java): Provides API endpoints to interact with the system.

4. **Error Handling**:
    - [`GlobalExceptionHandler`](https://github.com/NehaRajgaria/mainoTry/blob/main/src/main/java/com/example/mainoTry/controller/GlobalExceptionHandler.java): Centralized exception handling for API requests.

---

### Future Enhancements

- Use cloud storage (e.g., Amazon S3 or Azure Blob Storage) to store temporary files instead of relying on local disk.
- Store all the job related data in a database. 


## Resources
- [YouTube Data API v3 Documentation](https://developers.google.com/youtube/v3)
- [Video Upload Guide](https://developers.google.com/youtube/v3/guides/uploading_a_video)
- [OAuth 2.0 Setup](https://developers.google.com/youtube/v3/guides/auth/server-side-web-apps)