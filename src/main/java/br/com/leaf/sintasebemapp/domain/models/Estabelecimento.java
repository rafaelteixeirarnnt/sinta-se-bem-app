package br.com.leaf.sintasebemapp.domain.models;

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

    public Estabelecimento withId(UUID id) {
        return new Estabelecimento(id, this.nome, this.cnpj, this.telefone, this.whatsapp, this.email,
                this.inicioAtendimento, this.fimAtendimento, this.endereco, this.usuarioResponsavel);
    }

    public Estabelecimento withUsuarioResponsavel(Usuario usuarioResponsavel) {
        return new Estabelecimento(id, this.nome, this.cnpj, this.telefone, this.whatsapp, this.email,
                this.inicioAtendimento, this.fimAtendimento, this.endereco, usuarioResponsavel);
    }

    public Estabelecimento withEmail(String email) {
        return new Estabelecimento(id, this.nome, this.cnpj, this.telefone, this.whatsapp, email,
                this.inicioAtendimento, this.fimAtendimento, this.endereco, usuarioResponsavel);
    }

    public Estabelecimento withNome(String nome) {
        return new Estabelecimento(id, nome, this.cnpj, this.telefone, this.whatsapp, this.email,
                this.inicioAtendimento, this.fimAtendimento, this.endereco, usuarioResponsavel);
    }
}
