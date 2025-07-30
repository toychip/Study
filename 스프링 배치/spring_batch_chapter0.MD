# Spring Batch

## Spring Batch가 제공하는 영역

### 1. 핵심 실행 컴포넌트

#### Job과 Step
- **Job**: 배치 작업의 최상위 개념으로, 하나 이상의 Step으로 구성
- **Step**: 실제 배치 작업을 수행하는 단위로, Job의 구성 요소

#### JobLauncher
- Job을 실행하고 실행에 필요한 파라미터를 전달하는 역할
- 배치 작업 실행의 시작점
- 동기/비동기 실행 방식 지원

#### JobRepository
- 배치 처리의 모든 메타데이터를 저장하고 관리하는 핵심 저장소
- Job과 Step의 실행 정보(시작/종료 시간, 상태, 결과 등)를 기록
- 배치 작업의 모니터링이나 문제 발생 시 재실행에 활용

#### ExecutionContext
- Job과 Step 실행 중의 상태 정보를 key-value 형태로 담는 객체
- Job과 Step 간의 데이터 공유나 Job 재시작 시 상태 복원에 사용

### 2. 데이터 처리 컴포넌트 구현체

Spring Batch는 데이터를 '읽기-처리-쓰기' 방식으로 처리하며 이를 위한 다양한 구현체를 제공

#### ItemReader 구현체
다양한 데이터 소스로부터 데이터를 읽어올 수 있다:
- **JdbcCursorItemReader**: JDBC를 통한 데이터베이스 읽기
- **JpaPagingItemReader**: JPA를 사용한 페이징 방식 읽기
- **MongoCursorItemReader**: MongoDB 데이터 읽기
- **FlatFileItemReader**: CSV, TXT 등 파일 읽기
- **JsonItemReader**: JSON 파일 읽기

#### ItemWriter 구현체
처리된 데이터를 다양한 저장소에 저장할 수 있다:
- **JdbcBatchItemWriter**: JDBC를 통한 배치 데이터베이스 쓰기
- **JpaItemWriter**: JPA를 사용한 데이터베이스 쓰기
- **MongoItemWriter**: MongoDB 데이터 쓰기
- **FlatFileItemWriter**: 파일 쓰기
- **JsonFileItemWriter**: JSON 파일 쓰기

#### ItemProcessor
- 읽은 데이터를 처리하고 변환하는 중간 처리 단계
- 필터링, 변환, 검증 등의 로직 구현 가능

### 3. 배치 작업의 생명주기

1. **Job 시작**: JobLauncher가 Job을 실행
2. **Step 실행**: Job 내의 각 Step이 순차적으로 실행
3. **데이터 처리**: ItemReader → ItemProcessor → ItemWriter 순서로 처리
4. **메타데이터 저장**: JobRepository에 실행 정보 저장
5. **완료/실패**: 작업 완료 또는 실패 시 적절한 처리

## 개발자가 제어하는 영역

Spring Batch가 제공하는 컴포넌트들을 활용하여 개발자가 직접 작성하고 제어해야 하는 부분

### 1. Job/Step 구성

#### @Configuration을 사용한 배치 작업 정의
- Job과 Step의 실행 흐름을 정의
- 각 Step의 실행 순서와 조건을 설정
- Spring 컨테이너에 등록해 배치 잡의 동작을 구성
- Spring의 DI(의존성 주입)를 활용해 컴포넌트들을 조합

#### 기본 배치 잡 구성 예제
```java
@Bean
public Job dataTerminationJob(Step terminateStep) {
    return new JobBuilder("dataTerminationJob", jobRepository)
            .start(terminateStep)
            .build();
}

@Bean
public Step terminateStep(ItemReader<String> itemReader, ItemWriter<String> itemWriter) {
    return new StepBuilder("terminateStep", jobRepository)
            .<String, String>chunk(10, transactionManager)
            .reader(itemReader)
            .writer(itemWriter)
            .build();
}

@Bean
public ItemReader<String> itemReader() {
    // return ItemReader 구현체
}

@Bean
public ItemWriter<String> itemWriter() {
    // return ItemWriter 구현체
}
```

### 2. 데이터 처리 컴포넌트 활용

#### 세부 로직 구현
- Spring Batch는 ItemReader, ItemWriter 구현체를 제공하지만 세부 로직은 개발자가 직접 지정
- 예: FlatFileItemReader로 CSV 파일 읽을 때 컬럼 매핑 방식은 개발자가 지정
- SQL 쿼리 조건, 파일 포맷 등 구체적인 처리 로직 구현 필요

### 3. 단순 작업 처리

#### 직접 구현이 필요한 작업들
- 파일 복사, 디렉토리 정리, 알림 발송 등 단순 작업
- Spring Batch가 제공하는 포인트를 활용하여 구현
- 단순해 보여도 시스템 안정성에 중요한 역할

### 4. 커스텀 데이터 처리 컴포넌트

#### ItemReader/ItemWriter 직접 구현
- Spring Batch가 제공하지 않는 데이터 소스 처리 시 필요
- 예: 특정 데이터베이스, 새로운 포맷 등
- MongoDB Cursor 기반 ItemReader, Redis ItemReader/ItemWriter 등

#### ItemProcessor 구현
- 읽은 데이터를 가공하고 필터링하는 역할
- 비즈니스 로직의 핵심을 담당
- 개발자가 직접 구현해야 하는 핵심 컴포넌트

### 5. 개발자의 책임

1. **배치 작업 설계**: Job과 Step의 구조 설계
2. **비즈니스 로직 구현**: ItemProcessor를 통한 데이터 처리 로직
3. **데이터 소스 연결**: ItemReader/ItemWriter를 통한 데이터 입출력
4. **에러 처리**: 예외 상황에 대한 적절한 처리
5. **성능 최적화**: 청크 크기, 병렬 처리 등 최적화 설정

## 데이터 처리 컴포넌트 활용

### 핵심 컴포넌트
- **ItemReader**: 데이터 소스에서 데이터를 읽어오는 컴포넌트
- **ItemWriter**: 처리된 데이터를 저장소에 쓰는 컴포넌트  
- **ItemProcessor**: 읽은 데이터를 가공하고 필터링하는 컴포넌트

### 개발자가 직접 구현해야 하는 부분
1. **세부 로직**: 파일 포맷, SQL 쿼리 조건, 컬럼 매핑 방식 등
2. **비즈니스 로직**: ItemProcessor를 통한 데이터 가공 및 필터링
3. **커스텀 컴포넌트**: Spring Batch가 제공하지 않는 데이터 소스 처리

### 단순 작업 처리
- 파일 복사, 디렉토리 정리, 알림 발송 등
- Spring Batch가 제공하는 포인트를 활용하여 직접 구현

## 실제 배치 시스템 구축

### 두 가지 접근 방식

#### 1. 원시적 방식 (Raw Spring Batch)
- Spring Boot 없이 순수 Spring Batch만 사용
- 모든 설정을 수동으로 구성
- 필요한 Bean들을 수동 등록
- CommandLineJobRunner로 명령줄에서 배치 실행
- Spring Boot 편리함을 느끼기 위한 기초 학습용

#### 2. Spring Boot 방식
- Spring Boot 자동 설정 활용
- 복잡한 설정은 프레임워크가 자동 처리
- 개발자는 핵심 비즈니스 로직에만 집중
- JobLauncherApplicationRunner가 자동으로 배치 실행

**메인 애플리케이션 클래스**
```java
@SpringBootApplication
public class KillBatchSystemApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(KillBatchSystemApplication.class, args)));
    }
}
```

**System.exit() 사용 이유**
- 배치 작업 성공/실패 상태를 exit code로 외부 시스템에 전달
- 실무에서 배치 모니터링과 제어에 필수
- 정상 완료: 0, 실패: 0이 아닌 값 반환
- 외부 스케줄러나 모니터링 시스템에서 배치 실행 결과 판단 가능

### 핵심 차이점
- **원시적 방식**: 모든 설정을 수동으로 구성, CommandLineJobRunner 사용
- **Spring Boot 방식**: 자동 설정 활용, JobLauncherApplicationRunner가 자동으로 배치 실행
