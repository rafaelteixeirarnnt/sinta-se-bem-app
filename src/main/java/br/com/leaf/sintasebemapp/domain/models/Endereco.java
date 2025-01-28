package br.com.leaf.sintasebemapp.domain.models;

public record Endereco(String cidade,
                       String estado,
                       String bairro,
                       String cep,
                       String logradouro,
                       String numero,
                       String complemento) {
}
