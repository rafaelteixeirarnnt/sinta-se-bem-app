package br.com.leaf.sintasebemapp.infra.controller;

import br.com.leaf.sintasebemapp.domain.enums.DiaSemanaEnum;
import br.com.leaf.sintasebemapp.infra.dto.request.EstabelecimentoRequest;
import br.com.leaf.sintasebemapp.infra.dto.response.EstabelecimentoResponse;
import br.com.leaf.sintasebemapp.infra.services.EstabelecimentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EstabelecimentoControllerTest {

    @Mock
    private EstabelecimentoService service;

    private AutoCloseable mock;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        var controller = new EstabelecimentoController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class SalvaEstabelecimento {

        @Test
        void deveSalvar() throws Exception {
            var response = new EstabelecimentoResponse(UUID.randomUUID());

            var request = new EstabelecimentoRequest("12345678909", "nome", "68642960000199",
                    "12345678901", "12345678901", "cidade", "DF", "bairro", "71920890",
                    "logradouro", "numero", "complemento", "e@e.com",
                    LocalTime.of(9, 0), LocalTime.of(20, 0), Arrays.asList(DiaSemanaEnum.SEGUNDA, DiaSemanaEnum.TERCA));

            when(service.salvar(any(EstabelecimentoRequest.class))).thenAnswer(i -> response);

            mockMvc.perform(MockMvcRequestBuilders.post("/v1/estabelecimentos")
                            .contentType(APPLICATION_JSON)
                            .content(asJsonString(request))
                    ).andExpect(status().isCreated());
        }

    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(obj);
    }

}
