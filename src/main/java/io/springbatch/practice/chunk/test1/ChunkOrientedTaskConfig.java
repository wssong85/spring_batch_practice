package io.springbatch.practice.chunk.test1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkOrientedTaskConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkOrientedTask() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1())
            .next(step2())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
            .<String, String>chunk(3)
//            .reader(() -> {
//                System.out.println("step1 reader execute.");
//                return null;
//            })
            .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6", "item7")))
            .processor((ItemProcessor<String, String>) item -> {
                System.out.println("step1 processor execute.");
                Thread.sleep(300);
                System.out.println("item = " + item);
                return "my" + item;
            })
            .writer(items -> {
                System.out.println("step1 writer execute.");
                System.out.println("items = " + items);
            })
            .build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("step2 was execute.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
