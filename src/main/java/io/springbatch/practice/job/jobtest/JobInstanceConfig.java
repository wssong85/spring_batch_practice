package io.springbatch.practice.job.jobtest;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobInstanceConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobInstance() {
        return jobBuilderFactory.get("jobInstance")
            .start(step1())
            .next(step2())
            .build();
    }

    //    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    System.out.println("===================");
                    System.out.println(">> Hello Spring Batch 1 !!");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }
            })
            .build();
    }

    //    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet(new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    System.out.println("===================");
                    System.out.println(">> Hello Spring Batch 2 !!");
                    System.out.println("===================");
                    return RepeatStatus.FINISHED;
                }
            })
            .build();
    }
}
