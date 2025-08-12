package com.system.batch.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ZombieBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ZombieBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Tasklet zombieProcessCleanupTasklet() {
        return new ZombieProcessCleanupTasklet();
    }

    @Bean
    public Step zombieCleanupStep() {
        return new StepBuilder("zombieCleanupStep", jobRepository)
                // Tasklet과 transactionManager 설정
                .tasklet(zombieProcessCleanupTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Job zombieCleanupJob() {
        return new JobBuilder("zombieCleanupJob", jobRepository)
                .start(zombieCleanupStep())  // Step 등록
                .build();
    }
}
