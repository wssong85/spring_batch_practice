package io.springbatch.practice.job.steptest;

import io.springbatch.practice.tasklet.CustomTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepExecutionJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepExecutionJob() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className).start(step1()).next(step2()).next(step3()).build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("step1 was execute.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
//                System.out.println("step2 was execute.");
//                if (true) {
//                    throw new RuntimeException("step failed!!");
//                }
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("step3 was execute.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
