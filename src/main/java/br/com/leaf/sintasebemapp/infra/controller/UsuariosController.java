package br.com.leaf.sintasebemapp.infra.controller;

import br.com.leaf.sintasebemapp.infra.dto.request.UsuarioRequest;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioRecuperadoResponse;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioResponse;
import br.com.leaf.sintasebemapp.infra.exception.ErrorResponse;
import br.com.leaf.sintasebemapp.infra.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuariosController {

    private final UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Cadastra um novo usuário",
            description = "Cadastra um novo usuário com seus dados pessoais e endereço",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UsuarioResponse> salvar(@Valid @RequestBody UsuarioRequest request) {
        var response = this.usuarioService.salvar(request);

        var location = URI.create(String.format("/v1/usuarios/%s", response.id()));

        return ResponseEntity.created(location).build();
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
