package com.system.batch;

import org.springframework.batch.core.Job;

import java.util.List;
import java.util.Optional;

public record JobMetaData(String jobName, Job job) {
}

record Jobs(List<JobMetaData> jobMetaDatas) {
    public Optional<Job> findByJobName(final String jobName) {
        return jobMetaDatas.stream()
                .filter(j -> j.jobName().equals(jobName))
                .map(JobMetaData::job)
                .findFirst();
    }
}