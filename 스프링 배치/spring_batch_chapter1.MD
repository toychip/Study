## 배치 처리의 두 가지 방식: Chunk와 Tasklet

### Step의 두 가지 처리 모델
- **청크 지향 처리 (Chunk-Oriented Processing)**: 데이터 기반 처리 (읽기-처리-쓰기)
- **태스크릿 지향 처리 (Tasklet-Oriented Processing)**: 단순 작업 처리

### Tasklet 지향 처리

#### 사용 시나리오
- 매일 새벽 불필요한 로그 파일 삭제
- 특정 디렉토리에서 오래된 파일을 아카이브
- 사용자에게 단순한 알림 메시지 또는 이메일 발송
- 외부 API 호출 후 결과를 단순히 저장하거나 로깅

#### Tasklet 인터페이스
```java
@FunctionalInterface
public interface Tasklet {
    @Nullable
    RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception;
}
```

#### Tasklet 구현 예시
```java
@Slf4j
public class ZombieProcessCleanupTasklet implements Tasklet {
    private final int processesToKill = 10;
    private int killedProcesses = 0;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        killedProcesses++;
        log.info("프로세스 강제 종료... ({}/{})", killedProcesses, processesToKill);

        if (killedProcesses >= processesToKill) {
            log.info("시스템 안정화 완료. 모든 프로세스 제거.");
            return RepeatStatus.FINISHED;
        }

        return RepeatStatus.CONTINUABLE;
    }
}
```

#### RepeatStatus
- **FINISHED**: Step 완료, 다음 단계로 진행
- **CONTINUABLE**: 추가 실행 필요, execute() 메서드 반복 호출

#### RepeatStatus 사용 이유
- 짧은 트랜잭션을 활용한 안전한 배치 처리
- 반복문 대신 Spring Batch가 트랜잭션 관리
- 예외 발생 시에도 이미 처리된 데이터는 안전하게 보존

#### Tasklet을 Step으로 등록
```java
@Configuration
public class ZombieBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Tasklet zombieProcessCleanupTasklet() {
        return new ZombieProcessCleanupTasklet();
    }

    @Bean
    public Step zombieCleanupStep() {
        return new StepBuilder("zombieCleanupStep", jobRepository)
                .tasklet(zombieProcessCleanupTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Job zombieCleanupJob() {
        return new JobBuilder("zombieCleanupJob", jobRepository)
                .start(zombieCleanupStep())
                .build();
    }
}
```

#### ResourcelessTransactionManager
- DB 작업이 없는 Tasklet에서 사용
- 불필요한 DB 트랜잭션 처리 생략
```java
@Bean
public Step zombieCleanupStep() {
    return new StepBuilder("zombieCleanupStep", jobRepository)
            .tasklet(zombieProcessCleanupTasklet(), new ResourcelessTransactionManager())
            .build();
}
```

### Tasklet 사용 시나리오 예시

#### 1. 오래된 파일 삭제
```java
@Slf4j
public class DeleteOldFilesTasklet implements Tasklet {
    private final String path;
    private final int daysOld;
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        File dir = new File(path);
        long cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        log.info("파일 삭제: {}", file.getName());
                    }
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}
```

#### 2. 람다식으로 간단한 Tasklet 구현
```java
@Bean
public Step deleteOldRecordsStep() {
   return new StepBuilder("deleteOldRecordsStep", jobRepository)
           .tasklet((contribution, chunkContext) -> {
               int deleted = jdbcTemplate.update("DELETE FROM logs WHERE created < NOW() - INTERVAL 7 DAY");
               log.info("{}개의 오래된 레코드가 삭제되었습니다.", deleted);
               return RepeatStatus.FINISHED;
           }, transactionManager)
           .build();
}
```

### Tasklet 지향 처리 특징
- **단순 작업에 적합**: 알림 발송, 파일 복사, 오래된 데이터 삭제 등
- **Tasklet 인터페이스 구현**: 필요한 로직을 작성하고 StepBuilder.tasklet()에 전달
- **RepeatStatus로 실행 제어**: execute() 메서드 반환값으로 반복 여부 결정
- **트랜잭션 지원**: Spring Batch가 execute() 메서드 실행 전후로 트랜잭션 관리
