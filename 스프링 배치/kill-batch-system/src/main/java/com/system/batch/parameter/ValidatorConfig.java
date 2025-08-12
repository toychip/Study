package com.system.batch.parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public Job systemDestructionJob(
            JobRepository jobRepository,
            Step systemDestructionStep,
            SystemDestructionValidator validator) {
        return new JobBuilder("systemDestructionJob", jobRepository)
                .validator(validator)
                .start(systemDestructionStep)
                .build();
    }
}
