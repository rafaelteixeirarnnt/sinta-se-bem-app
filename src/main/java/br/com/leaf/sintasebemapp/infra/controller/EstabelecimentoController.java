package br.com.leaf.sintasebemapp.infra.controller;

import br.com.leaf.sintasebemapp.application.usecases.EstabelecimentoUseCase;
import br.com.leaf.sintasebemapp.domain.models.Estabelecimento;
import br.com.leaf.sintasebemapp.infra.dto.request.EstabelecimentoRequest;
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
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/estabelecimentos")
public class EstabelecimentoController {

    private final EstabelecimentoUseCase service;

    @Operation(
            tags = "2 - Cadastro de Estabelecimentos",
            summary = "Cadastra um novo estabelecimento",
            description = "Cadastra um novo estabelecimento com seus dados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Estabelecimento cadastrado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            description = "Mapa contendo o identificador do estabelecimento",
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
    public ResponseEntity<Map<String, UUID>> salvar(@Valid @RequestBody EstabelecimentoRequest request) {

        var response = this.service.salvar(Estabelecimento.toEstabelecimento(null, request.nome(), request.cnpj(), request.telefone(),
                request.whatsapp(), request.email(), request.inicioAtendimento(), request.fimAtendimento(), request.cidade(),
                request.estado(), request.bairro(), request.cep(), request.logradouro(), request.numero(), request.complemento(),
                request.cpfResponsavel()));

        var location = URI.create(String.format("/v1/estabelecimentos/%s", response.id()));

        return ResponseEntity.created(location)
                .body(Map.of("id", response.id()));
    }

}
