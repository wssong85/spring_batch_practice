package io.springbatch.practice.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final List<Job> jobs;

//    private final String jobName = "jobInstance";

//    @JobScope
//    @Value("${requestDate}")
//    public String jobParameter;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        String jobName = Arrays.stream(args.getSourceArgs()).findFirst().get();

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", String.valueOf(UUID.randomUUID()))
            .toJobParameters();

        Job job = jobs.stream()
            .filter(c -> c.getName().equals(jobName))
            .findFirst().get();

        jobLauncher.run(job, jobParameters);
    }
}
