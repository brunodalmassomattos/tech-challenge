package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import br.com.fiap.funcionalidadeDeCargaDeProdutos.FuncionalidadeDeCargaDeProdutosApplication;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.entity.Produto;
import br.com.fiap.funcionalidadeDeCargaDeProdutos.domain.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FuncionalidadeDeCargaDeProdutosApplication.class)
@SpringBatchTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "input.file=classpath:input/produtos-test.csv"
})
public class JobIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        produtoRepository.deleteAll();
    }

    @Test
    public void whenJobExecutionTest() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", "TestJob")
                .toJobParameters();

        List<Produto> produtos = produtoRepository.findAll();

        assertEquals(0, produtos.size());
    }
}