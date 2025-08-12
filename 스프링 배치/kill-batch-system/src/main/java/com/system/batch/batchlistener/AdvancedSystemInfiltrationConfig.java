package com.system.batch.batchlistener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;
import java.util.Random;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdvancedSystemInfiltrationConfig {
    private final InfiltrationPlanListener infiltrationPlanListener;

    @Bean
    public Job systemInfiltrationJob(JobRepository jobRepository, Step reconStep, Step attackStep) {
        return new JobBuilder("systemInfiltrationJob", jobRepository)
                .listener(infiltrationPlanListener)
                .start(reconStep)
                .next(attackStep)
                .build();
    }

    @Bean
    public Step reconStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("reconStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Map<String, Object> infiltrationPlan = (Map<String, Object>)
                            chunkContext.getStepContext()
                                    .getJobExecutionContext()
                                    .get("infiltrationPlan");
                    log.info("침투 준비 단계: {}", infiltrationPlan.get("targetSystem"));
                    log.info("필요한 도구: {}", infiltrationPlan.get("requiredTools"));
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step attackStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            Tasklet attackStepTasklet  // 주입받은 Tasklet 사용
    ) {
        return new StepBuilder("attackStep", jobRepository)
                .tasklet(attackStepTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet attackStepTasklet(
            @Value("#{jobExecutionContext['infiltrationPlan']}") Map<String, Object> infiltrationPlan
    ) {
        return (contribution, chunkContext) -> {
            log.info("시스템 공격 중: {}", infiltrationPlan.get("targetSystem"));
            log.info("목표: {}", infiltrationPlan.get("objective"));

            Random rand = new Random();
            boolean infiltrationSuccess = rand.nextBoolean();

            if (infiltrationSuccess) {
                log.info("침투 성공! 획득한 데이터: {}", infiltrationPlan.get("targetData"));
                contribution.getStepExecution().getJobExecution().getExecutionContext()
                        .put("infiltrationResult", "TERMINATED");
            } else {
                log.info("침투 실패. 시스템이 우리를 감지했다.");
                contribution.getStepExecution().getJobExecution().getExecutionContext()
                        .put("infiltrationResult", "DETECTED");
            }

            return RepeatStatus.FINISHED;
        };
    }
}
