package com.system.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        Optional<Job> jobOpt = jobs.findByJobName(jobToRun);

        if (jobOpt.isEmpty()) {
            System.err.println("존재하지 않는 잡: " + jobToRun);
            return;
        }

        try {
            jobLauncher.run(jobOpt.get(), new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            System.err.println("Job 실행 중 오류: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}