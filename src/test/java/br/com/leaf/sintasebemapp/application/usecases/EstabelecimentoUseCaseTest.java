package br.com.leaf.sintasebemapp.application.usecases;

import br.com.leaf.sintasebemapp.gateway.EstabelecimentoRepositoryGateway;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EstabelecimentoUseCaseTest {

    @InjectMocks
    private EstabelecimentoUseCase estabelecimentoUseCase;

    @Mock
    private EstabelecimentoRepositoryGateway gateway;



}
