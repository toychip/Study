package com.system.batch.batchlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BigBrotherJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("시스템 감시 시작. 모든 작업을 내 통제 하에 둔다.");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("작업 종료. 할당된 자원 정리 완료.");
        log.info("시스템 상태: {}", jobExecution.getStatus());
    }
}