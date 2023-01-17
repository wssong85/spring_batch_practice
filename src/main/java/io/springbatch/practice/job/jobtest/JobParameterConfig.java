package io.springbatch.practice.job.jobtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobParameterConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobParameter() {
        return jobBuilderFactory.get("jobParameter")
            .start(step1())
            .next(step2())
            .build();
    }

    //    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {

                JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();

                log.info("string: {}, long: {}, date: {}, double: {}",
                    jobParameters.getString("name"),
                    jobParameters.getLong("seq"),
                    jobParameters.getDate("date"),
                    jobParameters.getDouble("age"));

                System.out.println("===================");
                System.out.println(">> Hello Spring Batch 1 !!");
                System.out.println("===================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    //    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {

                JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();

                log.info("string: {}, long: {}, date: {}, double: {}",
                    jobParameters.getString("name"),
                    jobParameters.getLong("seq"),
                    jobParameters.getDate("date"),
                    jobParameters.getDouble("age"));

                Map<String, Object> parameters = chunkContext.getStepContext().getJobParameters();

                log.info("string: {}, long: {}, date: {}, double: {}",
                    parameters.get("name"),
                    parameters.get("seq"),
                    parameters.get("date"),
                    parameters.get("age"));

                System.out.println("===================");
                System.out.println(">> Hello Spring Batch 2 !!");
                System.out.println("===================");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
