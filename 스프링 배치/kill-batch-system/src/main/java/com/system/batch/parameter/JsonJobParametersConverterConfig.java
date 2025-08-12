package com.system.batch.parameter;

import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.converter.JsonJobParametersConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonJobParametersConverterConfig {

    // ./gradlew bootRun --args="--spring.batch.job.name=terminatorJob infiltrationTargets='{\"value\":\"판교서버실,안산데이터센터\",\"type\":\"java.lang.String\"}' missionName='{\"value\":\"파괴작전\",\"type\":\"java.lang.String\"}' securityLevel='{\"value\":3,\"type\":\"java.lang.Integer\"}' operationCommander='{\"value\":\"이순신\",\"type\":\"java.lang.String\"}'"
    @Bean
    public JobParametersConverter jobParametersConverter() {
        return new JsonJobParametersConverter();
    }
}
