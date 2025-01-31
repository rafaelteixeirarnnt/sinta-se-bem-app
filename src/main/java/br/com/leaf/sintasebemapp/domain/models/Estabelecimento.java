package br.com.leaf.sintasebemapp.domain.models;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.EnderecosEntity;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.entity.UsuariosEntity;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalTime;
import java.util.UUID;

public record Estabelecimento(UUID id, String nome,
                              String cnpj,
                              String telefone,
                              String whatsapp,
                              String email,
                              LocalTime inicioAtendimento,
                              LocalTime fimAtendimento,
                              Endereco endereco,
                              Usuario usuarioResponsavel) {

    public static Estabelecimento toEstabelecimento(UUID id, String nome, String cnpj, String telefone, String whatsapp,
                                                    String email, LocalTime inicioAtendimento, LocalTime fimAtendimento,
                                                    String cidade, String estado, String bairro, String cep, String logradouro,
                                                    String numero, String complemento, String cpfResponsavel) {
        return new Estabelecimento(id, nome, cnpj, telefone, whatsapp, email, inicioAtendimento, fimAtendimento,
                new Endereco(cidade, estado, bairro, cep, logradouro, numero, complemento),
                new Usuario(null, null, null, null, cpfResponsavel, null, null,
                        null, null, null));
    }
}
