package br.com.leaf.sintasebemapp.infra.dto.request;

import br.com.leaf.sintasebemapp.domain.enums.DiaSemanaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalTime;
import java.util.List;

public record EstabelecimentoRequest(

        @CPF
        @Schema(description = "CPF do responsável do estabelecimento", example = "12345678909")
        String cpfResponsavel,

        @NotBlank(message = "O nome do estabelecimento é obrigatório")
        @Length(min = 1, max = 100, message = "O nome do estabelecimento deve ter entre 1 e 100 caracteres")
        @Schema(description = "Nome do estabelecimento", example = "Farmácia do João")
        String nome,

        @CNPJ
        @Schema(description = "CNPJ do estabelecimento", example = "71418716000151")
        String cnpj,

        @Length(min = 10, max = 11, message = "O telefone deve ter entre 10 e 11 caracteres")
        @NotBlank(message = "O telefone é obrigatório")
        @Schema(description = "Telefone do usuário", example = "11999999999")
        String telefone,

        @Length(min = 11, max = 11, message = "O telefone deve ter 11 caracteres")
        @NotBlank(message = "O Whatsapp é obrigatório")
        @Schema(description = "Whatsapp do estabelecimento", example = "11999999999")
        String whatsapp,


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

        @Email
        @NotBlank(message = "O email é obrigatório")
        @Length(max = 50, message = "O email deve ter no máximo 50 caracteres")
        @Schema(description = "Email do estabelcimento", example = "e@e.com")
        String email,

        @NotNull
        @Schema(description = "Horário de início do atendimento", example = "09:00")
        LocalTime inicioAtendimento,

        @NotNull
        @Schema(description = "Horário de fim do atendimento", example = "18:00")
        LocalTime fimAtendimento,

        @NotNull
        @Schema(description = "Dias de atendimento", example = "[\"SEGUNDA\", \"TERCA\"]")
        List<DiaSemanaEnum> diasAtendimento

) {


}
