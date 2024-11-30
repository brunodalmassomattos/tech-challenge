package br.com.fiap.funcionalidadeDeCargaDeProdutos.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;

public class ProdutoItemReader {

    private final Resource resource;

    public ProdutoItemReader(Resource resource) {
        this.resource = resource;
    }

    public FlatFileItemReader<ProdutoInput> getReader() {
        return new FlatFileItemReaderBuilder<ProdutoInput>()
                .name("produtoItemReader")
                .resource(resource)
                .delimited()
                .delimiter(",")
                .names("nome", "descricao", "preco", "qtdEstoque", "categoriaDescricao")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(ProdutoInput.class);
                    setConversionService(new DefaultConversionService());
                }})
                .linesToSkip(1)
                .build();
    }
}
