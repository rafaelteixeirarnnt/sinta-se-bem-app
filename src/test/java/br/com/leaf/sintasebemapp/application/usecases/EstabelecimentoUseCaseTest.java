package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.EstabelecimentoValidationException;
import br.com.leaf.sintasebemapp.domain.models.Endereco;
import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.EstabelecimentoRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EstabelecimentoUseCaseTest {

    @InjectMocks
    private EstabelecimentoUseCase useCase;

    @Mock
    private EstabelecimentoRepositoryGateway gateway;

    @Mock
    private EstabelecimentoRepository repository;

    private Estabelecimento estabelecimento;

    @BeforeEach
    public void setUp() {
        estabelecimento = new Estabelecimento(null, "nome", "68642960000199", "12345678901", "12345678901",
                "e@e.com", LocalTime.of(9, 0), LocalTime.of(20, 0), new Endereco("cidade",
                "DF", "bairro", "71920890", "logradouro", "numero", "complemento"),
                new Usuario(null, null, null, null, "12345678909", null, null,
                        null, null, null));
    }

    @Nested
    class SalvarEstabelecimento {

        @Test
        void deveSalvarEstabelecimento() {

            estabelecimento = estabelecimento.withId(UUID.fromString("fadf47bc-fe76-468a-8349-e9f078e29008"));

            when(gateway.salvar(any(Estabelecimento.class))).thenReturn(estabelecimento);

            var response = useCase.salvar(estabelecimento);

            assertNotNull(response);
            assertThat(response.id(), notNullValue());
        }
    }

    @Nested
    class ValidaCpfUsuarioResponsavel {

        @Test
        void deveValidarSeCpfDoUsuarioResponsavelFoiInformado() {

            estabelecimento = estabelecimento.withUsuarioResponsavel(new Usuario(null, null, null, null, null, null, null, null, null, null));

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("CPF do usuário responsável é obrigatório"));

            verifyNoInteractions(repository);
        }

        @Test
        void deveValidarSeCpfDoUsuarioResponsavelPossuiOnzeDigitos() {

            estabelecimento = estabelecimento.withUsuarioResponsavel(new Usuario(null, null, null, null, "1234567890", null, null, null, null, null));

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("CPF do usuário responsável deve conter 11 dígitos"));

            verifyNoInteractions(repository);
        }
    }

    @Nested
    class ValidaEmail {
        @Test
        void deveValidarSeEmailFoiInformado() {

            estabelecimento = estabelecimento.withEmail(null);

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("O email é obrigatório"));

            verifyNoInteractions(repository);
        }

        @Test
        void deveValidarSeEmailPossuiEntreUmECinquentaCaracteres() {

            estabelecimento = estabelecimento.withEmail("123456789012345678901234567890123456789012345678901");

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("O email deve ter entre 1 e 50 caracteres"));

            verifyNoInteractions(repository);
        }

        @Test
        void deveValidarSeEmailEhValido() {

            estabelecimento = estabelecimento.withEmail("email");

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("Email inválido"));

            verifyNoInteractions(repository);
        }
    }

    @Nested
    class ValidaNomeEstabelecimento {
        @Test
        void deveValidarSeNomeDoEstabelecimentoFoiInformado() {

            estabelecimento = estabelecimento.withNome(null);

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));

            assertThat(e, notNullValue());
            assertThat(e.getMessage(), is("O nome do estabelecimento é obrigatório"));

            verifyNoInteractions(repository);
        }

        @Test
        void deveValidarSeNomeDoEstabelecimentoPossuiEntreUmECemCaracteres() {

            estabelecimento = estabelecimento.withNome("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");

            var e = assertThrows(EstabelecimentoValidationException.class, () -> useCase.salvar(estabelecimento));
            assertThat(e.getMessage(), is("O nome do estabelecimento deve ter entre 1 e 100 caracteres"));
        }
    }

}
