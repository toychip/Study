package com.system.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ManualBatchRunner {

    private final JobLauncher jobLauncher;
    private final Jobs jobs;

    public ManualBatchRunner(JobLauncher jobLauncher, List<Job> jobList) {
        this.jobLauncher = jobLauncher;
        this.jobs = new Jobs(
                jobList.stream()
                        .map(job -> new JobMetaData(job.getName(), job))
                        .toList()
        );
    }

    // 예시: 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * ?")
    public void runZombieCleanupJob() {
        String jobToRun = "zombieCleanupJob";
        jobs.jobMetaDatas().stream()
                .filter(j -> j.jobName().equals(jobToRun))
                .findFirst()
                .ifPresent(j -> {
                    try {
                        jobLauncher.run(j.job(), new JobParametersBuilder()
                                .addLong("time", System.currentTimeMillis())
                                .toJobParameters());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}