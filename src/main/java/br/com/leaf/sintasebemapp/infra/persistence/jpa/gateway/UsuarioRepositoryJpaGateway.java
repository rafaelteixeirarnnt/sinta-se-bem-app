package br.com.leaf.sintasebemapp.infra.persistence.jpa.gateway;

import br.com.leaf.sintasebemapp.application.exception.UsuarioValidationException;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.gateway.UsuarioRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.mapper.UsuarioMapper;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.EnderecoRepository;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.PerfilRepository;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioRepositoryJpaGateway implements UsuarioRepositoryGateway {

    private final UsuarioMapper mapper;

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository repository;
    private final EnderecoRepository enderecoRepository;

    public UsuarioRepositoryJpaGateway(UsuarioMapper mapper, PerfilRepository perfilRepository, UsuarioRepository repository, EnderecoRepository enderecoRepository) {
        this.mapper = mapper;
        this.perfilRepository = perfilRepository;
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        var entity = this.mapper.toEntity(usuario);

        var enderecosDB = this.enderecoRepository.save(entity.getEndereco());
        this.perfilRepository.findByDescricao(usuario.perfilEnum().name()).ifPresent(entity::setPerfil);

        entity.setEndereco(enderecosDB);

        var entityDB = this.repository.save(entity);
        return Usuario.toUsuarioID(entityDB.getId());
    }

    @Override
    public Usuario obterUsuarioPorId(UUID id) {
        return this.mapper.toUsuario(this.repository.findById(id).orElseThrow(() -> new UsuarioValidationException("Usuário não encontrado")));
    }

    @Override
    public Usuario obterUsuarioPorCPF(String cpf) {
        return this.mapper.toUsuario(this.repository.findByCpf(cpf).orElseThrow(() -> new UsuarioValidationException("Usuário não encontrado")));
    }
}
