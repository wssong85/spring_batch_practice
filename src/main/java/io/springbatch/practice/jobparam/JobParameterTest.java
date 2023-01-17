package io.springbatch.practice.jobparam;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class JobParameterTest implements ApplicationRunner {

    private final JobLauncher jobLauncher;

    private final List<Job> jobs;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        String jobName = Arrays.stream(args.getSourceArgs()).findFirst().get();

        Job job = jobs.stream()
            .filter(c -> c.getName().equals(jobName))
            .findFirst().get();

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user1")
            .addLong("seq", 2L)
            .addDate("date", new Date())
            .addDouble("age", 16.5).toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
