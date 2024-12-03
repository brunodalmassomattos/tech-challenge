package br.com.fiap.entrega.application.service;

import br.com.fiap.entrega.domain.entity.Endereco;
import br.com.fiap.entrega.domain.repository.EnderecoRepository;
import br.com.fiap.entrega.domain.service.EnderecoService;
import br.com.fiap.entrega.mocks.EnderecoMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnderecoServiceImplTest {

    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository repository;

    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        enderecoService = new EnderecoServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarEnderecoPorIdComSucesso() {
        Endereco enderecoEsperado = EnderecoMock.getEndereco();
        when(repository.buscarPorId(ArgumentMatchers.any(UUID.class))).thenReturn(enderecoEsperado);

        Endereco enderecoRetornado = enderecoService.buscarEnderecoPorId(UUID.randomUUID());

        assertThat(enderecoRetornado).isNotNull();
        assertThat(enderecoRetornado.getId()).isNotNull().isEqualTo(enderecoEsperado.getId());
        assertThat(enderecoRetornado.getCep()).isNotNull().isEqualTo(enderecoEsperado.getCep());
        verify(repository).buscarPorId(any(UUID.class));
    }
}