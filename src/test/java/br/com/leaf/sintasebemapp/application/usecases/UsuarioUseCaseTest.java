package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.NegocioException;
import br.com.leaf.sintasebemapp.domain.models.Endereco;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.UsuarioRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioUseCaseTest {

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    @Mock
    private UsuarioRepositoryGateway gateway;

    private final UsuarioMapper usuarioMapper = getMapper(UsuarioMapper.class);

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario(null, "Rafael", "r@r.com", "Aa1234567_", this.gerarCPF(), "11999999999",
                new Endereco("São Paulo", "SP", "Pinheiros", "71910000", "Rua 25 Sul",
                        "123", null),
                false, null, LocalDate.of(2000, 1, 31));
    }

    @Test
    void deveSalvarUsuario() {

        this.usuario = usuario.withId(UUID.fromString("fadf47bc-fe76-468a-8349-e9f078e29008"));

        when(gateway.salvar(any(Usuario.class))).thenReturn(usuario);

        var usuarioResponse = usuarioUseCase.salvar(usuario);

        assertNotNull(usuarioResponse);
        assertThat(usuarioResponse.id(), notNullValue());

        verify(gateway, times(1)).salvar(usuario);
    }

    @Test
    void deveValidarSeUsuarioTemAoMenosDoisCaracteresNoNome() {

        usuario = usuario.withNome("R");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Nome deve ter no mínimo 2 caracteres"));

        verifyNoInteractions(gateway);
    }

    @Test
    void deveValidarSeEmailEhValido() {

        usuario = usuario.withEmail("r@r");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Email inválido"));

        verifyNoInteractions(gateway);
    }

    @Test
    void deveValidarSeExisteSenha() {

        usuario = usuario.withSenha(null);

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Senha é obrigatória"));
    }

    @Test
    void deveValidarSeSenhaPossuiDezCaracteres() {

        usuario = usuario.withSenha("1234567");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Senha deve ter 10 caracteres"));
    }

    @Test
    void deveValidarSeSenhaPossuiLetraMinusculaMaiusculaNumeroECaracteresEspeciais() {

        usuario = usuario.withSenha("1234567890");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Senha inválida"));
    }

    @Test
    void deveValidarSeTelefonePossuiDezOuOnzeDigitos() {

        usuario = usuario.withTelefone("119999999");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Telefone deve ter 10 ou 11 dígitos"));
    }

    @Test
    void deveValidarSeTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros() {

        usuario = usuario.withTelefone("x119999999");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Telefone deve conter apenas números"));
    }

    @Test
    void deveValidarSeUsuarioTemEndereco() {

        usuario = usuario.withEndereco(null);

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Endereço é obrigatório"));
    }

    @Test
    void deveValidarSeEnderecoTemCamposObrigatorios() {

        usuario = usuario.withEndereco(new Endereco(null, null, null, null, null,
                null, null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Todos os campos do endereço são obrigatórios"));
    }

    @Test
    void deveValidarSeUsuarioTemCpfValido() {

        usuario = usuario.withCpf("1234567890");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("CPF inválido"));
    }

    @Test
    void deveValidarSeNumeroCpfEhDiferente() {

        usuario = usuario.withCpf("11111111111");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("CPF inválido"));
    }

    @Test
    void deveValidarSeCpfPrimeiroDigitoVerificadorEhValido() {

        usuario = usuario.withCpf("11111111121");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Primeiro digito verificador inválido"));
    }

    @Test
    void deveValidarSeCpfSegundoDigitoVerificadorEhValido() {

        usuario = usuario.withCpf("11111111112");

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Segundo digito verificador inválido"));
    }

    @Test
    void deveValidarLogradouro() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "Pinheiros", "71910000", null,
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Logradouro é obrigatório"));
    }

    @Test
    void deveValidarQuantidadeDeCaracteresDoLogradouroMenorQue2() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "Pinheiros", "71910000", "Ru",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarQuantidadeDeCaracteresDoLogradouroMaiorQue100() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "Pinheiros", "71910000",
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarBairro() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", null, "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Bairro é obrigatório"));
    }

    @Test
    void deveValidarBairroSePossuiQuantidadeMinima2() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "P", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarBairroSePossuiQuantidadeMaxima100() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
                "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarUfSeEhValida() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "U", "Pinheiros", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("UF inválida"));
    }

    @Test
    void deveValidarCidade() {
        usuario = usuario.withEndereco(new Endereco(null, "SP", "Pinheiros", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Cidade é obrigatório"));
    }

    @Test
    void deveValidarCidadeSePossuiQuantidadeMinima2() {
        usuario = usuario.withEndereco(new Endereco("S", "SP", "Pinheiros", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarCidadeSePossuiQuantidadeMaxima100() {
        usuario = usuario.withEndereco(new Endereco("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
                "SP", "Pinheiros", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres"));
    }

    @Test
    void deveValidarEstado() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", null, "Pinheiros", "71910000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Estado é obrigatório"));
    }

    @Test
    void deveValidarCep() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "Pinheiros", null, "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("CEP é obrigatório"));
    }

    @Test
    void deveValidarCepComOitoDigitos() {
        usuario = usuario.withEndereco(new Endereco("São Paulo", "SP", "Pinheiros", "7191000", "Rua 25 Sul",
                "123", null));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("CEP inválido"));
    }

    @Test
    void validarDtNascimento() {
        usuario = usuario.withDtNascimento(LocalDate.now().plusDays(1));

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Usuário deve ser maior de idade"));
    }

    @Test
    void validarSeTemDtNascimento() {
        usuario = usuario.withDtNascimento(null);

        var e = assertThrows(NegocioException.class, () -> usuarioUseCase.salvar(usuario));

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Data de nascimento é obrigatória"));
    }

    private String gerarCPF() {
        var random = new Random();

        var cpf = new int[11];

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10); // Gera um número de 0 a 9
        }

        cpf[9] = calcularDigitoVerificador(cpf, 10);

        cpf[10] = calcularDigitoVerificador(cpf, 11);

        return format("%d%d%d%d%d%d%d%d%d%d%d",
                cpf[0], cpf[1], cpf[2],
                cpf[3], cpf[4], cpf[5],
                cpf[6], cpf[7], cpf[8],
                cpf[9], cpf[10]);
    }

    private int calcularDigitoVerificador(int[] cpf, int pesoInicial) {
        var soma = 0;

        for (int i = 0; i < pesoInicial - 1; i++) {
            soma += cpf[i] * (pesoInicial - i);
        }

        var resto = soma % 11;

        return (resto < 2) ? 0 : (11 - resto);
    }



}
