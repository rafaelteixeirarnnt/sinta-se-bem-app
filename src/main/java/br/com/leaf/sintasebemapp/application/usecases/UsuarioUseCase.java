package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.NegocioException;
import br.com.leaf.sintasebemapp.application.exception.UsuarioValidationException;
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
        } catch (UsuarioValidationException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validarCamposUsuario(Usuario usuario) {
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
        } catch (UsuarioValidationException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    private void validarCPFJaCadastrado(String cpf) {
        try {
            var usuario = this.gateway.obterUsuarioPorCPF(cpf);
            if (usuario != null) {
                throw new NegocioException("CPF já cadastrado");
            }
        } catch (UsuarioValidationException e) {
            return;
        }
    }

    private void validarSeUsuarioMaiorDeIdade(LocalDate localDate) {
        if (localDate == null) {
            throw new UsuarioValidationException("Data de nascimento é obrigatória");
        }

        if (localDate.isAfter(LocalDate.now().minusYears(18))) {
            throw new UsuarioValidationException("Usuário deve ser maior de idade");
        }
    }

    private void validarNome(Usuario usuario) throws UsuarioValidationException {
        if (usuario.nome().length() < 2) {
            throw new UsuarioValidationException("Nome deve ter no mínimo 2 caracteres");
        }
    }

    private void validarSeEmailEhValido(Usuario usuario) throws UsuarioValidationException {
        if (!usuario.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new UsuarioValidationException("Email inválido");
        }
    }

    private void validarSenha(String senha) throws UsuarioValidationException {
        if (senha == null) {
            throw new UsuarioValidationException("Senha é obrigatória");
        }
        if (senha.length() != 10) {
            throw new UsuarioValidationException("Senha deve ter 10 caracteres");
        }

        boolean temLetraMinuscula = senha.matches(".*[a-z].*");
        boolean temLetraMaiuscula = senha.matches(".*[A-Z].*");
        boolean temNumero = senha.matches(".*[0-9].*");
        boolean temCaractereEspecial = senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        if (!temLetraMinuscula || !temLetraMaiuscula || !temNumero || !temCaractereEspecial) {
            throw new UsuarioValidationException("Senha inválida");
        }
    }

    private void validarTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros(Usuario usuario) throws UsuarioValidationException {
        if (usuario.telefone().length() < 10 || usuario.telefone().length() > 11) {
            throw new UsuarioValidationException("Telefone deve ter 10 ou 11 dígitos");
        }

        if (!usuario.telefone().matches("[0-9]+")) {
            throw new UsuarioValidationException("Telefone deve conter apenas números");
        }
    }

    private void validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            throw new UsuarioValidationException("CPF inválido");
        }

        if (cpf.chars().distinct().count() == 1) {
            throw new UsuarioValidationException("CPF inválido");
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
            throw new UsuarioValidationException("Primeiro digito verificador inválido");
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
            throw new UsuarioValidationException("Segundo digito verificador inválido");
        }
    }

    private void validarCamposEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new UsuarioValidationException("Endereço é obrigatório");
        }
        if (endereco.cidade() == null && endereco.estado() == null && endereco.bairro() == null &&
                endereco.cep() == null && endereco.logradouro() == null && endereco.numero() == null) {
            throw new UsuarioValidationException("Todos os campos do endereço são obrigatórios");
        }
    }

    private void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank()) {
            throw new UsuarioValidationException("Logradouro é obrigatório");
        }
        if (!(logradouro.length() > 2 && logradouro.length() < 100)) {
            throw new UsuarioValidationException("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new UsuarioValidationException("Bairro é obrigatório");
        }
        if (!(bairro.length() > 2 && bairro.length() < 100)) {
            throw new UsuarioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new UsuarioValidationException("Cidade é obrigatório");
        }
        if (!(cidade.length() > 2 && cidade.length() < 100)) {
            throw new UsuarioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new UsuarioValidationException("Estado é obrigatório");
        }
        var temUF = estado.length() == 2;

        if (!temUF) {
            throw new UsuarioValidationException("UF inválida");
        }

        EstadosEnum.getBySigla(estado);
    }

    private void validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new UsuarioValidationException("CEP é obrigatório");
        }
        if (cep.matches("[0-9]{8}")) {
            return;
        }
        throw new UsuarioValidationException("CEP inválido");
    }

    public Usuario obterUsuarioPorId(UUID id) {
        return this.gateway.obterUsuarioPorId(id);
    }
}
