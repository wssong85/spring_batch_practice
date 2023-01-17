package io.springbatch.practice.job.steptest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleTestJob() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1())
            .next(step2())
//            .next(step3())
//            .next(step4())
//            .next(step5())
            .build();
    }
    
//    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("step1 was execute.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
//    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .<String, String>chunk(3)
            .reader(() -> {
                System.out.println("step2 reader execute.");
                return null;
            })
            .processor((ItemProcessor<String, String>) item -> {
                System.out.println("step2 processor execute.");
                return null;
            })
            .writer(items -> {
                System.out.println("step2 writer execute.");
            })
            .build();
    }
//    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
            .partitioner(step1())
            .gridSize(2)
            .build();
    }
//    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
            .job(job())
            .build();
    }
//    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
            .flow(flow())
            .build();
    }
//    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .next(step3())
            .build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step2()).end();
        return flowBuilder.build();
    }
}
