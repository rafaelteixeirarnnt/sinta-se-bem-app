package br.com.leaf.sintasebemapp.gateway;

import br.com.leaf.sintasebemapp.domain.models.Usuario;

import java.util.UUID;

public interface UsuarioRepositoryGateway {

    Usuario salvar(Usuario usuario);

    Usuario obterUsuarioPorId(UUID id);

    Usuario obterUsuarioPorCPF(String cpf);
}
