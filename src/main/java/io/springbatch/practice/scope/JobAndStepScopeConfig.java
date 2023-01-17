package io.springbatch.practice.scope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobAndStepScopeConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobAndStepScope() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1(null))
            .next(step2())
            .listener(new JobListener())
            .build();
    }

    @JobScope
    @Bean
    public Step step1(@Value("#{jobParameters['message']}") String message) {

        log.info("step1 message={}", message);

        return stepBuilderFactory.get("step1")
            .tasklet(tasklet1(null))
            .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet(tasklet2(null))
            .listener(new CustomStepListener())
            .build();
    }

    @Bean
    @StepScope
    public Tasklet tasklet1(@Value("#{jobExecutionContext['name']}") String name) {

        log.info("tesklet1 name={}", name);

        return (stepContribution, chunkContext) -> {
            System.out.println("tesklet1 has executed.");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @StepScope
    public Tasklet tasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {

        log.info("tesklet2 name2={}", name2);
        return (stepContribution, chunkContext) -> {
            System.out.println("tasklet2 has executed.");
            return RepeatStatus.FINISHED;
        };
    }

}
