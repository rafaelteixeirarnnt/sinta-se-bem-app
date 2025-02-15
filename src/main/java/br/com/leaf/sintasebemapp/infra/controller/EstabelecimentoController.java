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

import java.net.URI;

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
                    @ApiResponse(responseCode = "201", description = "Estabelecimento cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)),
                            description = "Erro interno no servidor"
                    )
            })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseEntity> salvar(@Valid @RequestBody EstabelecimentoRequest request) {
        var response = this.service.salvar(request);

        var location = URI.create(String.format("/v1/estabelecimentos/%s", response.id()));

        return ResponseEntity.created(location).build();
    }

}
