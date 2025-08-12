package com.system.batch.batchlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class InfiltrationPlanListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        Map<String, Object> infiltrationPlan = generateInfiltrationPlan();
        jobExecution.getExecutionContext().put("infiltrationPlan", infiltrationPlan);
        log.info("새로운 침투 계획이 준비됐다: {}",  infiltrationPlan.get("targetSystem"));
    }

    private Map<String, Object> generateInfiltrationPlan() {
        List<String> targets = List.of(
                "판교 서버실", "안산 데이터센터"
        );
        List<String> objectives = List.of(
                "kill -9 실행", "rm -rf 전개", "chmod 000 적용", "/dev/null로 리다이렉션"
        );
        List<String> targetData = List.of(
                "코어 덤프 파일", "시스템 로그", "설정 파일", "백업 데이터"
        );
        List<String> requiredTools = List.of(
                "USB 킬러", "널 바이트 인젝터", "커널 패닉 유발기", "메모리 시퍼너"
        );

        Random rand = new Random();

        Map<String, Object> infiltrationPlan = new HashMap<>();
        infiltrationPlan.put("targetSystem", targets.get(rand.nextInt(targets.size())));
        infiltrationPlan.put("objective", objectives.get(rand.nextInt(objectives.size())));
        infiltrationPlan.put("targetData", targetData.get(rand.nextInt(targetData.size())));
        infiltrationPlan.put("requiredTools", requiredTools.get(rand.nextInt(requiredTools.size())));

        return infiltrationPlan;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String infiltrationResult = (String) jobExecution.getExecutionContext().get("infiltrationResult");
        Map<String, Object> infiltrationPlan = (Map<String, Object>)
                jobExecution.getExecutionContext().get("infiltrationPlan");

        log.info("타겟 '{}' 침투 결과: {}", infiltrationPlan.get("targetSystem"), infiltrationResult);

        if ("TERMINATED".equals(infiltrationResult)) {
            log.info("시스템 제거 완료. 다음 타겟 검색 중...");
        } else {
            log.info("철수한다. 다음 기회를 노리자.");
        }
    }
}