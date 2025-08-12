package com.system.batch.parameter;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Step;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepBeanTracer {

    private final ApplicationContext ctx;
    private final ConfigurableListableBeanFactory bf;

    @PostConstruct
    void traceStepBeans() {
        String[] names = ctx.getBeanNamesForType(Step.class);
        for (String name : names) {
            try {
                BeanDefinition bd = bf.getBeanDefinition(name);
                log.info("STEP BEAN: {}\n  - resource: {}\n  - factoryBean: {}\n  - factoryMethod: {}\n  - beanClassName: {}",
                        name,
                        bd.getResourceDescription(),
                        bd.getFactoryBeanName(),
                        bd.getFactoryMethodName(),
                        bd.getBeanClassName());
            } catch (Exception e) {
                log.info("STEP BEAN: {} (BeanDefinition 조회 불가, 실제 타입: {})",
                        name, ctx.getBean(name).getClass().getName());
            }
        }
    }
}