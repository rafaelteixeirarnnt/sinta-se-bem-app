package br.com.leaf.sintasebemapp.infra.dto.response;

import java.util.UUID;

public record UsuarioResponse(UUID id) {

    public static UsuarioResponse toUsuarioResponse(UUID id) {
        return new UsuarioResponse(id);
    }
}
