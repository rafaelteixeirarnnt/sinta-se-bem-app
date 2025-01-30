package br.com.leaf.sintasebemapp.infra.services;

import br.com.leaf.sintasebemapp.application.usecases.UsuarioUseCase;
import br.com.leaf.sintasebemapp.domain.models.Endereco;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.infra.dto.request.UsuarioRequest;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioRecuperadoResponse;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioService(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    public UsuarioResponse salvar(UsuarioRequest request) {
        return usuarioUseCase.salvar(Usuario.toUsuario(null, request.nome(), request.email(), request.senha(), request.cpf(), request.telefone(),
                request.cidade(), request.estado(), request.bairro(), request.cep(), request.logradouro(), request.numero(), request.complemento(),
                request.prestadorServico(), request.dtNascimento()));
    }

    public UsuarioRecuperadoResponse obterUsuario(UUID id) {
        var usuario = usuarioUseCase.obterUsuarioPorId(id);
        return null;
    }
}
