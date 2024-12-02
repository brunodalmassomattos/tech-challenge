package br.com.fiap.funcionalidadeDeCargaDeProdutos.api;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importProdutoJob;

    @PostMapping("/import")
    public String importProdutos() {
        try {
            jobLauncher.run(importProdutoJob, new JobParametersBuilder()
                    .addString("JobID", UUID.randomUUID().toString())
                    .toJobParameters());
            return "Job iniciado com sucesso.";
        } catch (Exception e) {
            return "Erro ao iniciar o job: " + e.getMessage();
        }
    }
}

