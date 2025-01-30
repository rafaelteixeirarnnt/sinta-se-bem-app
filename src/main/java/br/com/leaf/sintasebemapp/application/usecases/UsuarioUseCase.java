package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.NegocioException;
import br.com.leaf.sintasebemapp.application.exception.UsuarioNegocioValidationException;
import br.com.leaf.sintasebemapp.domain.enums.EstadosEnum;
import br.com.leaf.sintasebemapp.domain.models.Endereco;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.UsuarioRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioResponse;

import java.time.LocalDate;
import java.util.UUID;

import static java.lang.Character.getNumericValue;

public class UsuarioUseCase {

    private final UsuarioRepositoryGateway gateway;

    public UsuarioUseCase(UsuarioRepositoryGateway usuarioRepositoryGateway) {
        this.gateway = usuarioRepositoryGateway;
    }

    public UsuarioResponse salvar(Usuario usuario) {
        try {
            this.validarCamposUsuario(usuario);
            var usuarioSalvo = gateway.salvar(usuario);
            return UsuarioResponse.toUsuarioResponse(usuarioSalvo.id());
        } catch (UsuarioNegocioValidationException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public void validarCamposUsuario(Usuario usuario) {
        try {
            validarNome(usuario);
            validarSeEmailEhValido(usuario);
            validarSenha(usuario.senha());
            validarTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros(usuario);
            validarCPF(usuario.cpf());
            validarCPFJaCadastrado(usuario.cpf());
            validarCamposEndereco(usuario.endereco());
            validarLogradouro(usuario.endereco().logradouro());
            validarBairro(usuario.endereco().bairro());
            validarCidade(usuario.endereco().cidade());
            validarEstado(usuario.endereco().estado());
            validarCep(usuario.endereco().cep());
            validarSeUsuarioMaiorDeIdade(usuario.dtNascimento());
        } catch (UsuarioNegocioValidationException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validarCPFJaCadastrado(String cpf) {
        try {
            var usuario = this.gateway.obterUsuarioPorCPF(cpf);
            if (usuario != null) {
                throw new NegocioException("CPF já cadastrado");
            }
        } catch (UsuarioNegocioValidationException e) {
            return;
        }
    }

    private void validarSeUsuarioMaiorDeIdade(LocalDate localDate) {
        if (localDate == null) {
            throw new UsuarioNegocioValidationException("Data de nascimento é obrigatória");
        }

        if (localDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new UsuarioNegocioValidationException("Usuário deve ser maior de idade");
        }
    }

    public void validarNome(Usuario usuario) throws UsuarioNegocioValidationException {
        if (usuario.nome().length() < 2) {
            throw new UsuarioNegocioValidationException("Nome deve ter no mínimo 2 caracteres");
        }
    }

    public void validarSeEmailEhValido(Usuario usuario) throws UsuarioNegocioValidationException {
        if (!usuario.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new UsuarioNegocioValidationException("Email inválido");
        }
    }

    public void validarSenha(String senha) throws UsuarioNegocioValidationException {
        if (senha == null) {
            throw new UsuarioNegocioValidationException("Senha é obrigatória");
        }
        if (senha.length() != 10) {
            throw new UsuarioNegocioValidationException("Senha deve ter 10 caracteres");
        }

        boolean temLetraMinuscula = senha.matches(".*[a-z].*");
        boolean temLetraMaiuscula = senha.matches(".*[A-Z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temCaractereEspecial = senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        if (!temLetraMinuscula || !temLetraMaiuscula || !temNumero || !temCaractereEspecial) {
            throw new UsuarioNegocioValidationException("Senha inválida");
        }
    }

    public void validarTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros(Usuario usuario) throws UsuarioNegocioValidationException {
        if (usuario.telefone().length() < 10 || usuario.telefone().length() > 11) {
            throw new UsuarioNegocioValidationException("Telefone deve ter 10 ou 11 dígitos");
        }

        if (!usuario.telefone().matches("[0-9]+")) {
            throw new UsuarioNegocioValidationException("Telefone deve conter apenas números");
        }

    }

    public void validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            throw new UsuarioNegocioValidationException("CPF inválido");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new UsuarioNegocioValidationException("CPF inválido");
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != getNumericValue(cpf.charAt(9))) {
            throw new UsuarioNegocioValidationException("Primeiro digito verificador inválido");
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        int numericValue = getNumericValue(cpf.charAt(10));

        if (numericValue != segundoDigito) {
            throw new UsuarioNegocioValidationException("Segundo digito verificador inválido");
        }
    }

    public void validarCamposEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new UsuarioNegocioValidationException("Endereço é obrigatório");
        }
        if (endereco.cidade() == null && endereco.estado() == null && endereco.bairro() == null &&
                endereco.cep() == null && endereco.logradouro() == null && endereco.numero() == null) {
            throw new UsuarioNegocioValidationException("Todos os campos do endereço são obrigatórios");
        }
    }

    public void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank()) {
            throw new UsuarioNegocioValidationException("Logradouro é obrigatório");
        }
        if (!(logradouro.length() > 2 && logradouro.length() < 100)) {
            throw new UsuarioNegocioValidationException("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new UsuarioNegocioValidationException("Bairro é obrigatório");
        }
        if (!(bairro.length() > 2 && bairro.length() < 100)) {
            throw new UsuarioNegocioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new UsuarioNegocioValidationException("Cidade é obrigatório");
        }
        if (!(cidade.length() > 2 && cidade.length() < 100)) {
            throw new UsuarioNegocioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new UsuarioNegocioValidationException("Estado é obrigatório");
        }
        var temUF = estado.length() == 2;

        if (!temUF) {
            throw new UsuarioNegocioValidationException("UF inválida");
        }

        EstadosEnum.getBySigla(estado);
    }

    public void validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new UsuarioNegocioValidationException("CEP é obrigatório");
        }
        if (cep.matches("[0-9]{8}")) {
            return;
        }
        throw new UsuarioNegocioValidationException("CEP inválido");
    }

    public Usuario obterUsuarioPorId(UUID id) {
        return this.gateway.obterUsuarioPorId(id);
    }
}
