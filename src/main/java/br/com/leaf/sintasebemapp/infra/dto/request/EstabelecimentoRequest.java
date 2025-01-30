package br.com.leaf.sintasebemapp.infra.dto.request;

import java.util.UUID;

public record EstabelecimentoRequest(UUID idUsuario, String nome, String cnpj, String telefone, String cidade,
                                     String estado, String bairro, String cep, String logradouro, String numero,
                                     String complemento) {

}
