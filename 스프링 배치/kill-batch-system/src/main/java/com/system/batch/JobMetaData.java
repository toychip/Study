package com.system.batch;

import org.springframework.batch.core.Job;

import java.util.List;

public record JobMetaData(String jobName, Job job) {
}

record Jobs(List<JobMetaData> jobMetaDatas) {}