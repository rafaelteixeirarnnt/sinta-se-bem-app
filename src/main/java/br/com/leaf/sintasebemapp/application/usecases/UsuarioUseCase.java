package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.NegocioException;
import br.com.leaf.sintasebemapp.application.exception.UsuarioNegocioValidationException;
import br.com.leaf.sintasebemapp.domain.enums.EstadosEnum;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.UsuarioRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioResponse;

import java.util.UUID;

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
            validarCamposEndereco(usuario);
            validarLogradouro(usuario.endereco().logradouro());
            validarBairro(usuario.endereco().bairro());
            validarCidade(usuario.endereco().cidade());
            validarEstado(usuario.endereco().estado());
            validarCep(usuario.endereco().cep());
        } catch (UsuarioNegocioValidationException e) {
            throw new NegocioException(e.getMessage());
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
        if (senha == null || senha.length() != 10) {
            return;
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
            return;
        }

        if (cpf.chars().distinct().count() == 1) {
            return;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        Character.getNumericValue(cpf.charAt(10));
    }

    public void validarCamposEndereco(Usuario usuario) {
        if (!(usuario.endereco().cidade() != null && usuario.endereco().estado() != null && usuario.endereco().bairro() != null &&
                usuario.endereco().cep() != null && usuario.endereco().logradouro() != null && usuario.endereco().numero() != null &&
                usuario.endereco().complemento() != null)) {
            throw new UsuarioNegocioValidationException("Todos os campos do endereço são obrigatórios");
        }
    }

    public void validarLogradouro(String logradouro) {
        if (!(logradouro.length() > 2 && logradouro.length() < 100)) {
            throw new UsuarioNegocioValidationException("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarBairro(String bairro) {
        if (!(bairro.length() > 2 && bairro.length() < 100)) {
            throw new UsuarioNegocioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarCidade(String cidade) {
        if (!(cidade.length() > 2 && cidade.length() < 100)) {
            throw new UsuarioNegocioValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    public void validarEstado(String estado) {
        var temUF = estado.length() == 2;

        if (!temUF) {
            throw new UsuarioNegocioValidationException("UF inválida");
        }

        EstadosEnum.getBySigla(estado);
    }

    public void validarCep(String cep) {
        if (cep.matches("[0-9]{8}")) {
            return;
        }
        throw new UsuarioNegocioValidationException("CEP inválido");
    }

    public Usuario obterUsuarioPorId(UUID id) {
        return this.gateway.obterUsuarioPorId(id);
    }
}
