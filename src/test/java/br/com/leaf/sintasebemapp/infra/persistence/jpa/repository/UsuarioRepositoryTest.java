package br.com.leaf.sintasebemapp.infra.persistence.jpa.repository;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.EnderecosEntity;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.PerfisEntity;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.UsuariosEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository repository;

    private AutoCloseable mock;

    private UsuariosEntity usuario;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);

        usuario = new UsuariosEntity();

        var perfil = new PerfisEntity();
        perfil.setId(UUID.randomUUID());
        perfil.setDescricao("Cliente");

        var endereco = new EnderecosEntity();
        endereco.setId(UUID.randomUUID());
        endereco.setCidade("Brasília");
        endereco.setEstado("DF");
        endereco.setBairro("Asa Norte");
        endereco.setCep("70000000");
        endereco.setLogradouro("SQN 000");
        endereco.setNumero("00");
        endereco.setComplemento("Apto 000");

        usuario.setId(UUID.randomUUID());
        usuario.setNome("Rafael");
        usuario.setEmail("r@r.com");
        usuario.setDtNascimento(LocalDate.of(2000, 1, 1));
        usuario.setCpf("12345678909");
        usuario.setPrestadorServico(false);
        usuario.setTelefone("61999999999");

        usuario.setPerfil(perfil);
        usuario.setEndereco(endereco);
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class SalvaUsuario {

        @Test
        void deveSalvarUsuario() {

            var usuarioDB = usuario;
            var entity = new UsuariosEntity();

            when(repository.save(any(UsuariosEntity.class))).thenReturn(usuarioDB);

            entity.setId(null);

            usuarioDB = repository.save(entity);

            assertNotNull(usuarioDB);
            assertNotNull(usuarioDB.getId());

            verify(repository, times(1)).save(any(UsuariosEntity.class));
        }
    }

    @Nested
    class RecuperaUsuario {

        @Test
        void deveRecuperarUsuario() {

            var usuarioDB = usuario;

            when(repository.findById(any(UUID.class))).thenReturn(Optional.of(usuarioDB));

            var usuario = repository.findById(UUID.randomUUID()).orElse(null);

            assertNotNull(usuario);
            assertThat(usuario.getId(), notNullValue());
            assertThat(usuario.getId(), is(usuarioDB.getId()));

            verify(repository, times(1)).findById(any(UUID.class));
        }
    }
}
