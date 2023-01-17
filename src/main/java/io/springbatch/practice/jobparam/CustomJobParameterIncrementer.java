package io.springbatch.practice.jobparam;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomJobParameterIncrementer implements JobParametersIncrementer {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmdss");
    @Override
    public JobParameters getNext(JobParameters parameters) {

        String id = format.format(new Date());
        return new JobParametersBuilder().addString("run.id", id).toJobParameters();
    }
}
