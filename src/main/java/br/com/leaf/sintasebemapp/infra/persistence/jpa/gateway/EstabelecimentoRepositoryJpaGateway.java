package br.com.leaf.sintasebemapp.infra.persistence.jpa.gateway;

import br.com.leaf.sintasebemapp.application.exception.EstabelecimentoValidationException;
import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.gateway.EstabelecimentoRepositoryGateway;
import br.com.leaf.sintasebemapp.infra.mapper.EstabelecimentoMapper;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.EnderecoRepository;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.EstabelecimentoRepository;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class EstabelecimentoRepositoryJpaGateway implements EstabelecimentoRepositoryGateway {

    private final EstabelecimentoMapper mapper;

    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository repository;
    private final EnderecoRepository enderecoRepository;

    public EstabelecimentoRepositoryJpaGateway(EstabelecimentoMapper mapper, UsuarioRepository usuarioRepository,
                                               EstabelecimentoRepository repository, EnderecoRepository enderecoRepository) {
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
    }

    public Estabelecimento salvar(Estabelecimento estabelecimento) {
        var entity = this.mapper.toEntity(estabelecimento);

        this.enderecoRepository.save(entity.getEndereco());
        var usuario = this.usuarioRepository.findByCpf(entity.getUsuarioResponsavel().getCpf()).orElseThrow(() -> new EstabelecimentoValidationException("Usuário não encontrado"));
        entity.setUsuarioResponsavel(usuario);

        return this.mapper.toEstabelecimento(this.repository.save(entity));
    }

    @Override
    public Estabelecimento obterEstabelecimentoPorCNPJ(String cnpj) {
        var estabelecimento = this.repository.findByCnpj(cnpj).orElse(null);

        if (estabelecimento == null) {
            return null;
        }

        return this.mapper.toEstabelecimento(estabelecimento);
    }
}
