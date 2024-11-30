package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.CategoriaRepository;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Value("${input.file}")
    private Resource inputResource;

    @Bean
    public Job importProdutoJob(JobRepository jobRepository, Step importStep) {
        return new JobBuilder("importProdutoJob", jobRepository)
                .preventRestart()
                .start(importStep)
                .build();
    }

    @Bean
    public Step importStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                           FlatFileItemReader<ProdutoInput> reader, ProdutoItemProcessor processor,
                           RepositoryItemWriter<Produto> writer) {
        return new StepBuilder("importStep", jobRepository)
                .<ProdutoInput, Produto>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<ProdutoInput> reader() {
        return new ProdutoItemReader(inputResource).getReader();
    }

    @Bean
    public ProdutoItemProcessor processor(CategoriaRepository categoriaRepository) {
        return new ProdutoItemProcessor(categoriaRepository);
    }

    @Bean
    public RepositoryItemWriter<Produto> writer(ProdutoRepository produtoRepository) {
        RepositoryItemWriter<Produto> writer = new RepositoryItemWriter<>();
        writer.setRepository(produtoRepository);
        writer.setMethodName("save");
        return writer;
    }
}