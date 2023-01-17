package io.springbatch.practice.job.jobtest;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DbJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job dbJob() {
        return jobBuilderFactory.get("dbJob")
            .start(dbStep1())
            .next(dbStep2())
            .build();
    }

    @Bean
    public Step dbStep1() {
        return stepBuilderFactory.get("dbStep1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("===================");
                System.out.println(">> DB Batch 1 !!");
                System.out.println("===================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step dbStep2() {
        return stepBuilderFactory.get("dbStep2")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("===================");
                System.out.println(">> DB Batch 2 !!");
                System.out.println("===================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
