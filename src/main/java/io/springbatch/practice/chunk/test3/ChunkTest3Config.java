package io.springbatch.practice.chunk.test3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkTest3Config {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkTest3() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1())
            .next(step2())
//            .incrementer(new RunIdIncrementer())
            .build();
    }

//    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<String, String>chunk(5)
            .reader(itemReader())
            .writer(itemWriter())
            .build();
    }

//    @Bean
    public ItemStreamWriter<? super String> itemWriter() {
        return new CustomItemWriter2();
    }

//    @Bean
    public ItemStreamReader<String> itemReader() {
        List<String> items = new ArrayList<>(10);

        for (int i = 0; i <= 10; i++) {
            items.add(String.valueOf(i));
        }
        return new CustomerItemStreamReader(items);
    }

//    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
                System.out.println("step2 was execute.");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
