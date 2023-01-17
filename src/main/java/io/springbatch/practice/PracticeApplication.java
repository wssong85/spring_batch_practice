package io.springbatch.practice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class PracticeApplication {


    // 외부 parameter args 주입 => name=user1 timeMillis(long)=1010L date(date)=2023/01/17 age(double)=18.5
    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, jobParameter());
//        SpringApplication.run(PracticeApplication.class, hardJobParameter());
    }

    public static String[] jobParameter() {
        String[] jobParameter = {"timeMillis(long)=" + System.currentTimeMillis()};
        return jobParameter;
    }

    // 1673949628869
    public static String[] hardJobParameter() {
        String[] jobParameter = {"timeMillis(long)=" + "1673949628869"};
        return jobParameter;
    }
}
