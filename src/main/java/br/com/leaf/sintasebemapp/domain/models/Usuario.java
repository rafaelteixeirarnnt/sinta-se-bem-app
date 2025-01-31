package br.com.leaf.sintasebemapp.domain.models;

import br.com.leaf.sintasebemapp.infra.persistence.jpa.enums.PerfilEnum;

import java.time.LocalDate;
import java.util.UUID;

public record Usuario(UUID id,
                      String nome,
                      String email,
                      String senha,
                      String cpf,
                      String telefone,
                      Endereco endereco,
                      Boolean prestadorServico,
                      PerfilEnum perfilEnum, LocalDate dtNascimento) {

    public static Usuario toUsuario(UUID id, String nome, String email, String senha, String cpf, String telefone, String cidade,
                                    String estado, String bairro, String cep, String logradouro, String numero, String complemento,
                                    Boolean prestadorServico, LocalDate dtNascimento) {
        return new Usuario(id, nome, email, senha, cpf, telefone,
                new Endereco(cidade, estado, bairro, cep, logradouro, numero, complemento),
                prestadorServico,
                prestadorServico ? PerfilEnum.PRESTADOR : PerfilEnum.CLIENTE, dtNascimento);
    }

    public static Usuario toUsuarioID(UUID id) {
        return new Usuario(id, null, null, null, null, null, null,
                null, null, null);
    }

    public Usuario withNome(String novoNome) {
        return new Usuario(id, novoNome, email, senha, cpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withEmail(String novoEmail) {
        return new Usuario(id, nome, novoEmail, senha, cpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withSenha(String novaSenha) {
        return new Usuario(id, nome, email, novaSenha, cpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withCpf(String novoCpf) {
        return new Usuario(id, nome, email, senha, novoCpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withTelefone(String novoTelefone) {
        return new Usuario(id, nome, email, senha, cpf, novoTelefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withEndereco(Endereco novoEndereco) {
        return new Usuario(id, nome, email, senha, cpf, telefone, novoEndereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withPrestadorServico(Boolean novoPrestadorServico) {
        return new Usuario(id, nome, email, senha, cpf, telefone, endereco, novoPrestadorServico,
                novoPrestadorServico ? PerfilEnum.PRESTADOR : PerfilEnum.CLIENTE, dtNascimento);
    }

    public Usuario withPerfilEnum(PerfilEnum novoPerfilEnum) {
        return new Usuario(id, nome, email, senha, cpf, telefone, endereco, prestadorServico, novoPerfilEnum, dtNascimento);
    }

    public Usuario withId(UUID id) {
        return new Usuario(id, nome, email, senha, cpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }

    public Usuario withDtNascimento(LocalDate dtNascimento) {
        return new Usuario(id, nome, email, senha, cpf, telefone, endereco, prestadorServico, perfilEnum, dtNascimento);
    }
}
