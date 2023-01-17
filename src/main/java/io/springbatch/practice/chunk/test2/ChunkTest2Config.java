package io.springbatch.practice.chunk.test2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkTest2Config {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ChunkTest2() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1())
            .next(step2())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
            .<Customer, Customer>chunk(3)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    private ItemWriter<? super Customer> itemWriter() {
        return new CustomItemWriter();
    }

    private ItemProcessor<? super Customer, Customer> itemProcessor() {
        return new CustomerItemProcessor();
    }

    private ItemReader<Customer> itemReader() {
        return new CustomItemReader(Arrays.asList(
            new Customer("customer1"),
            new Customer("customer2"),
            new Customer("customer3"),
            new Customer("customer4"),
            new Customer("customer5"),
            new Customer("customer6"),
            new Customer("customer7"),
            new Customer("customer8")
        ));
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
