package io.springbatch.practice.simplejob;

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
public class SimpleTestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CustomTasklet customTasklet;

    @Bean
    public Job simpleTestJob() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className).start(step1()).next(step2()).build();
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
//        return stepBuilderFactory.get("step2")
//            .tasklet((contribution, chunkContext) -> customTasklet.execute(contribution, chunkContext))
//            .build();
        return stepBuilderFactory.get("step2")
            .tasklet(new CustomTasklet())
            .build();
    }
}
