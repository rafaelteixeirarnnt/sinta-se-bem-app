package br.com.leaf.sintasebemapp.main;

import br.com.leaf.sintasebemapp.application.usecases.UsuarioUseCase;
import br.com.leaf.sintasebemapp.infra.persistence.jpa.gateway.UsuarioRepositoryJpaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioRepositoryJpaGateway usuarioGateway) {
        return new UsuarioUseCase(usuarioGateway);
    }
}
