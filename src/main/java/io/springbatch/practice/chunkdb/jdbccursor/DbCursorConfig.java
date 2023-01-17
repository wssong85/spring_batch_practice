package io.springbatch.practice.chunkdb.jdbccursor;

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
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class DbCursorConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;
    private final DataSource datasource;

    @Bean
    public Job dbCursor() {

        String className = this.getClass().getSuperclass().getSimpleName();
        return jobBuilderFactory.get(className)
            .start(step1())
            .next(step2())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
            .<Customer, Customer2>chunk(10)
            .reader(customerItemReader())
            .processor((ItemProcessor<Customer, Customer2>) item -> new Customer2(item))
            .writer(jpaCustomItemWriter())
            .build();
    }

    public ItemWriter<? super Customer2> jpaCustomItemWriter() {
        return new JpaItemWriterBuilder<Customer2>()
            .usePersist(true)
            .entityManagerFactory(entityManagerFactory)
            .build();
    }


    private ItemReader<Customer> customerItemReader() {
        return new JdbcCursorItemReaderBuilder<Customer>()
            .name("jdbcCustomerItemReader")
            .fetchSize(chunkSize)
            .sql("select id, firstName, lastName, birthdate from customer where firstName like ? order by id")
            .beanRowMapper(Customer.class)
            .queryArguments("f%")
            .dataSource(datasource)
            .build();
    }

    private ItemReader<Customer> jdbcTemplateItemReader() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        List<Customer> results =
            jdbcTemplate
                .queryForList("select id, firstName, lastName, birthdate from customer order by id", Customer.class);
        return new ListItemReader<>(results);
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
