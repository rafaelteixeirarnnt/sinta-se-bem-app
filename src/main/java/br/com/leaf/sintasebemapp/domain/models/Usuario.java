package br.com.leaf.sintasebemapp.domain.models;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.enums.PerfilEnum;

import java.util.UUID;

public record Usuario(UUID id,
                      String nome,
                      String email,
                      String senha,
                      String cpf,
                      String telefone,
                      Endereco endereco,
                      Boolean prestadorServico,
                      PerfilEnum perfilEnum) {

    public static Usuario toUsuario(UUID id, String nome, String email, String senha, String cpf, String telefone, String cidade,
                   String estado, String bairro, String cep, String logradouro, String numero, String complemento,
                   Boolean prestadorServico) {
        return new Usuario(id, nome, email, senha, cpf, telefone,
                new Endereco(cidade, estado, bairro, cep, logradouro, numero, complemento),
                prestadorServico,
                prestadorServico ? PerfilEnum.PRESTADOR : PerfilEnum.CLIENTE);
    }

    public static Usuario toUsuarioID(UUID id) {
        return new Usuario(id, null, null, null, null, null, null,
                null, null);
    }

}
