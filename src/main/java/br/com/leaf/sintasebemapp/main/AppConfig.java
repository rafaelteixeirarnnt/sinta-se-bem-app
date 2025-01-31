package br.com.leaf.sintasebemapp.main;

import br.com.leaf.sintasebemapp.application.usecases.EstabelecimentoUseCase;
import br.com.leaf.sintasebemapp.application.usecases.UsuarioUseCase;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.gateway.EstabelecimentoRepositoryJpaGateway;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.gateway.UsuarioRepositoryJpaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioRepositoryJpaGateway usuarioGateway) {
        return new UsuarioUseCase(usuarioGateway);
    }

    @Bean
    public EstabelecimentoUseCase estabelecimentoUseCase(EstabelecimentoRepositoryJpaGateway estabelecimentoGateway) {
        return new EstabelecimentoUseCase(estabelecimentoGateway);
    }
}
