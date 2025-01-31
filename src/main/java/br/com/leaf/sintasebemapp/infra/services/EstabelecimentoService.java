package br.com.leaf.sintasebemapp.infra.services;

import br.com.leaf.sintasebemapp.application.usecases.EstabelecimentoUseCase;
import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.infra.dto.request.EstabelecimentoRequest;
import br.com.leaf.sintasebemapp.infra.dto.response.EstabelecimentoResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstabelecimentoService {

    private final EstabelecimentoUseCase useCase;

    public EstabelecimentoService(EstabelecimentoUseCase useCase) {
        this.useCase = useCase;
    }

    public EstabelecimentoResponse salvar(@Valid EstabelecimentoRequest request) {
        return this.useCase.salvar(Estabelecimento.toEstabelecimento(null, request.nome(), request.cnpj(), request.telefone(),
                request.whatsapp(), request.email(), request.inicioAtendimento(), request.fimAtendimento(), request.cidade(),
                request.estado(), request.bairro(), request.cep(), request.logradouro(), request.numero(), request.complemento(),
                request.cpfResponsavel()));
    }

}
