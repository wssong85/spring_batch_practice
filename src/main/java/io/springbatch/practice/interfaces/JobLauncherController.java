package io.springbatch.practice.interfaces;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@Slf4j
//@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final List<Job> jobs;

    private final JobLauncher jobLauncher;

    private final String jobName = "JobLauncherConfig";

    private final BasicBatchConfigurer basicBatchConfigurer;

    // sync
//    @PostMapping("/batch")
//    public String launch(@RequestBody Member member)
    public String launch(Member member)
        throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("id", member.getId())
            .addDate("date", new Date())
            .toJobParameters();

        Job job = jobs.stream()
            .filter(c -> c.getName().equals(jobName))
            .findFirst().get();


        JobExecution run = jobLauncher.run(job, jobParameters);

        log.info("JobExecution execution exit code: {} ", run.getExitStatus());

        return "batch completed.";
    }


//    @PostMapping("/async/batch")
//    public String asyncLaunch(@RequestBody Member member)
    public String asyncLaunch(Member member)
        throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("id", member.getId())
            .addDate("date", new Date())
            .toJobParameters();

        Job job = jobs.stream()
            .filter(c -> c.getName().equals(jobName))
            .findFirst().get();

        SimpleJobLauncher simpleJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        JobExecution run = simpleJobLauncher.run(job, jobParameters);

        log.info("JobExecution execution exit code: {} ", run.getExitStatus());

        return "batch completed.";
    }
}
