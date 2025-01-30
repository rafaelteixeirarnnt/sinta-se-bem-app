package br.com.leaf.sintasebemapp.infra.controller;

import br.com.leaf.sintasebemapp.infra.dto.request.EstabelecimentoRequest;
import br.com.leaf.sintasebemapp.infra.services.EstabelecimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoService service;

    @Operation(
            tags = "2 - Cadastro de Estabelecimentos",
            summary = "Cadastra um novo estabelecimento",
            description = "Cadastra um novo estabelecimento com seus dados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estabelecimento cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> salvar(@Valid @RequestBody EstabelecimentoRequest request) {
        this.service.salvar(request);
        return ResponseEntity.ok().build();
    }

}
