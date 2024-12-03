package br.com.fiap.mslogistica.persistence.repository;

import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import br.com.fiap.mslogistica.domain.entity.Endereco;
import br.com.fiap.mslogistica.domain.repository.EnderecoRepository;
import br.com.fiap.mslogistica.mocks.EnderecoMock;
import br.com.fiap.mslogistica.persistence.data.DataEnderecoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EnderecoRepositoryImplTest {

    @Mock
    private DataEnderecoRepository repository;

    private AutoCloseable openMocks;

    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        enderecoRepository = new EnderecoRepositoryImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveBuscarEnderecoPorIdComSucesso() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(EnderecoMock.getEndereco()));

        Endereco endereco = enderecoRepository.buscarPorId(UUID.randomUUID());

        assertThat(endereco).isNotNull();
        assertThat(endereco.getCep()).isEqualTo(EnderecoMock.getEndereco().getCep());
        verify(repository, times(1)).findById(any(UUID.class));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarEnderecoPorId() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        UUID idEndereco = UUID.randomUUID();
        assertThatThrownBy(() -> enderecoRepository.buscarPorId(idEndereco))
                .isInstanceOf(ControllerNotFoundException.class)
                .hasMessage(String.format("Não existe endereço para id: %s", idEndereco.toString()));

        verify(repository, times(1)).findById(any(UUID.class));
    }
}