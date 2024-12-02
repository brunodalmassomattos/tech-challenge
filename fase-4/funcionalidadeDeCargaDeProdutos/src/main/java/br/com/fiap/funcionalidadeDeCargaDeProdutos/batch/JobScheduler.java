package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importProdutoJob;

    @Scheduled(cron = "${job.cron.expression}")
    public void perform() throws Exception {
        jobLauncher.run(importProdutoJob, new JobParametersBuilder()
                .addString("JobID", UUID.randomUUID().toString())
                .toJobParameters());
    }
}
