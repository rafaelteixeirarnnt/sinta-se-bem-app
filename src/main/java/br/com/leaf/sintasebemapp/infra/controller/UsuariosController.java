package br.com.leaf.sintasebemapp.infra.controller;

import br.com.leaf.sintasebemapp.application.usecases.UsuarioUseCase;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.infra.dto.request.UsuarioRequest;
import br.com.leaf.sintasebemapp.infra.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuariosController {

    private final UsuarioUseCase service;

    @Operation(
            tags = "1 - Cadastro de Usuários",
            summary = "Cadastra um novo usuário",
            description = "Cadastra um novo usuário com seus dados pessoais e endereço",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            description = "Mapa contendo o identificador do usuário",
                                            example = "{\"id\": \"550e8400-e29b-41d4-a716-446655440000\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                            description = "Erro interno no servidor"
                    )
            })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, UUID>> salvar(@Valid @RequestBody UsuarioRequest request) {
        var response = this.service.salvar(Usuario.toUsuario(null, request.nome(), request.email(), request.senha(), request.cpf(), request.telefone(),
                request.cidade(), request.estado(), request.bairro(), request.cep(), request.logradouro(), request.numero(), request.complemento(),
                request.prestadorServico(), request.dtNascimento()));

        var location = URI.create(String.format("/v1/usuarios/%s", response.id()));

        return ResponseEntity.created(location)
                .body(Map.of("id", response.id()));
    }

//    @Operation(summary = "Recupera um usuário",
//            description = "Recupera um usuário pelo seu identificador",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso"),
//                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            })
//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<UsuarioRecuperadoResponse> obterUsuario(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.usuarioService.obterUsuario(id));
//    }


}
