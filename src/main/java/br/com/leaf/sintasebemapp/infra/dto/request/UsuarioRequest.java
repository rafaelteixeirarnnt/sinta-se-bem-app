package br.com.leaf.sintasebemapp.infra.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Length(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        @Schema(description = "Nome do usuário", example = "João da Silva")
        String nome,

        @NotBlank(message = "O email é obrigatório")
        @Length(max = 50, message = "O email deve ter no máximo 50 caracteres")
        @Schema(description = "Email do usuário", example = "a@a.com")
        String email,

        @Length(min = 10, max = 10, message = "A senha deve ter 10 caracteres")
        @NotBlank(message = "A senha é obrigatória")
        @Schema(description = "Senha do usuário", example = "Aa1234567_")
        String senha,

        @CPF(message = "O CPF é inválido")
        @Schema(description = "CPF do usuário", example = "12345678909")
        String cpf,

        @Length(min = 10, max = 11, message = "O telefone deve ter entre 10 e 11 caracteres")
        @NotBlank(message = "O telefone é obrigatório")
        @Schema(description = "Telefone do usuário", example = "11999999999")
        String telefone,

        @NotBlank(message = "A cidade é obrigatória")
        @Length(min = 2, max = 100, message = "A cidade deve ter entre 2 e 100 caracteres")
        @Schema(description = "Cidade do usuário", example = "São Paulo")
        String cidade,

        @Length(min = 2, max = 2, message = "O estado deve ter 2 caracteres")
        @NotBlank(message = "O estado é obrigatória")
        @Schema(description = "Estado do usuário", example = "SP")
        String estado,

        @Length(max = 100, message = "O bairro deve ter 100 caracteres")
        @NotBlank(message = "O bairro é obrigatória")
        @Schema(description = "Bairro do usuário", example = "Vila Mariana")
        String bairro,

        @Length(max = 8, message = "O bairro deve ter 8 caracteres")
        @NotBlank(message = "O cep é obrigatória")
        @Schema(description = "CEP do usuário", example = "04101300")
        String cep,

        @Length(max = 100, message = "O bairro deve ter 100 caracteres")
        @NotBlank(message = "O logradouro é obrigatória")
        @Schema(description = "Logradouro do usuário", example = "Rua Vergueiro")
        String logradouro,

        @Length(max = 10, message = "O número deve ter 10 caracteres")
        @Schema(description = "Número do logradouro", example = "123")
        String numero,

        @Length(max = 100, message = "O complemento deve ter 100 caracteres")
        @Schema(description = "Complemento do logradouro", example = "Apto 123")
        String complemento,


        @Schema(description = "Usuário é prestador de Serviço", defaultValue = "true")
        Boolean prestadorServico) {
}
