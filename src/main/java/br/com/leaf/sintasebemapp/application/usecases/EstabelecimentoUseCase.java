package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.application.exception.EstabelecimentoValidationException;

import br.com.leaf.sintasebemapp.domain.enums.EstadosEnum;
import br.com.leaf.sintasebemapp.domain.models.Endereco;
import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.EstabelecimentoRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.dto.response.EstabelecimentoResponse;

import java.util.regex.Pattern;

public class EstabelecimentoUseCase {

    private final EstabelecimentoRepositoryGateway gateway;

    public EstabelecimentoUseCase(EstabelecimentoRepositoryGateway gateway) {
        this.gateway = gateway;
    }

    public EstabelecimentoResponse salvar(Estabelecimento estabelecimento) {

        this.validarEstabelecimento(estabelecimento);
        var estResponse = this.gateway.salvar(estabelecimento);
        return new EstabelecimentoResponse(estResponse.id());
    }

    private void validarEstabelecimento(Estabelecimento estabelecimento) {
        this.validarNomeEstabelecimento(estabelecimento.nome());
        this.validarCNPJ(estabelecimento.cnpj());
        this.validarCNPJJahCadastrado(estabelecimento.cnpj());
        this.validarTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros(estabelecimento.telefone());
        this.validarWhatsappPossuiOnzeDigitosESeTodosSaoNumeros(estabelecimento.whatsapp());
        this.validarEmail(estabelecimento.email());
        this.validarEndereco(estabelecimento.endereco());
        this.validarUsuarioResponsavel(estabelecimento.usuarioResponsavel());
    }

    private void validarCNPJJahCadastrado(String cnpj) {
        var estabelecimento = this.gateway.obterEstabelecimentoPorCNPJ(cnpj);
        if (estabelecimento != null) {
            throw new EstabelecimentoValidationException("CNPJ já cadastrado");
        }
    }

    private void validarUsuarioResponsavel(Usuario usuario) {
        if (usuario == null) {
            throw new EstabelecimentoValidationException("Usuário responsável é obrigatório");
        }
        if (usuario.cpf() == null) {
            throw new EstabelecimentoValidationException("CPF do usuário responsável é obrigatório");
        }
        if (usuario.cpf().length() != 11) {
            throw new EstabelecimentoValidationException("CPF do usuário responsável deve conter 11 dígitos");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new EstabelecimentoValidationException("O email é obrigatório");
        }
        if (email.length() > 50) {
            throw new EstabelecimentoValidationException("O email deve ter entre 1 e 50 caracteres");
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new EstabelecimentoValidationException("Email inválido");
        }
    }

    private void validarNomeEstabelecimento(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new EstabelecimentoValidationException("O nome do estabelecimento é obrigatório");
        }
        if (nome.length() > 100) {
            throw new EstabelecimentoValidationException("O nome do estabelecimento deve ter entre 1 e 100 caracteres");
        }
    }

    private void validarCNPJ(String cnpj) {
        if (cnpj == null || !Pattern.compile("\\d{14}").matcher(cnpj).matches()) {
            throw new EstabelecimentoValidationException("O CNPJ deve conter 14 dígitos");
        }

        // Verifica se todos os dígitos são iguais (exemplo: 11111111111111)
        if (cnpj.chars().distinct().count() == 1) {
            throw new EstabelecimentoValidationException("Números repetidos não são permitidos");
        }

        // Calcula os dígitos verificadores
        int primeiroDV = calcularDigitoVerificador(cnpj, 12);
        int segundoDV = calcularDigitoVerificador(cnpj, 13);

        // Verifica se os dígitos verificadores estão corretos
        boolean digitoVerificador = primeiroDV == Character.getNumericValue(cnpj.charAt(12)) &&
                segundoDV == Character.getNumericValue(cnpj.charAt(13));

        if (!digitoVerificador) {
            throw new EstabelecimentoValidationException("Digito verificador inválido");
        }
    }

    private static int calcularDigitoVerificador(String cnpj, int posicao) {
        int[] pesos = (posicao == 12) ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
                : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    private void validarTelefonePossuiDezOuOnzeDigitosESeTodosSaoNumeros(String telefone) throws EstabelecimentoValidationException {
        if (telefone == null) {
            throw new EstabelecimentoValidationException("Telefone é obrigatório");
        }
        if (telefone.length() < 10 || telefone.length() > 11) {
            throw new EstabelecimentoValidationException("Telefone deve ter 10 ou 11 dígitos");
        }

        if (!telefone.matches("[0-9]+")) {
            throw new EstabelecimentoValidationException("Telefone deve conter apenas números");
        }
    }

    private void validarWhatsappPossuiOnzeDigitosESeTodosSaoNumeros(String whatsapp) throws EstabelecimentoValidationException {
        if (whatsapp == null) {
            throw new EstabelecimentoValidationException("Whatsapp é obrigatório");
        }
        if (whatsapp.length() > 11) {
            throw new EstabelecimentoValidationException("Whatsapp deve ter 11 dígitos");
        }

        if (!whatsapp.matches("[0-9]+")) {
            throw new EstabelecimentoValidationException("Whatsapp deve conter apenas números");
        }
    }

    private void validarEndereco(Endereco endereco) {
        validarCidade(endereco.cidade());
        validarEstado(endereco.estado());
        validarBairro(endereco.bairro());
        validarCep(endereco.cep());
        validarLogradouro(endereco.logradouro());
    }

    private void validarCamposEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new EstabelecimentoValidationException("Endereço é obrigatório");
        }
        if (endereco.cidade() == null && endereco.estado() == null && endereco.bairro() == null &&
                endereco.cep() == null && endereco.logradouro() == null && endereco.numero() == null) {
            throw new EstabelecimentoValidationException("Todos os campos do endereço são obrigatórios");
        }
    }

    private void validarLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isBlank()) {
            throw new EstabelecimentoValidationException("Logradouro é obrigatório");
        }
        if (!(logradouro.length() > 2 && logradouro.length() < 100)) {
            throw new EstabelecimentoValidationException("O logradouro deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new EstabelecimentoValidationException("Bairro é obrigatório");
        }
        if (!(bairro.length() > 2 && bairro.length() < 100)) {
            throw new EstabelecimentoValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarCidade(String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new EstabelecimentoValidationException("Cidade é obrigatório");
        }
        if (!(cidade.length() > 2 && cidade.length() < 100)) {
            throw new EstabelecimentoValidationException("A cidade deve ter no mínimo 2 caracteres e no máximo 100 caracteres");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new EstabelecimentoValidationException("Estado é obrigatório");
        }
        var temUF = estado.length() == 2;

        if (!temUF) {
            throw new EstabelecimentoValidationException("UF inválida");
        }

        EstadosEnum.getBySigla(estado);
    }

    private void validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new EstabelecimentoValidationException("CEP é obrigatório");
        }
        if (cep.matches("[0-9]{8}")) {
            return;
        }
        throw new EstabelecimentoValidationException("CEP inválido");
    }

}
