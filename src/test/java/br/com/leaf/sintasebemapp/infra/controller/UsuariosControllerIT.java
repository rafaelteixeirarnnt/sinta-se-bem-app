package br.com.leaf.sintasebemapp.infra.controller;


import br.com.leaf.sintasebemapp.application.usecases.UsuarioUseCase;
import br.com.leaf.sintasebemapp.domain.models.Usuario;
import br.com.leaf.sintasebemapp.infra.dto.request.UsuarioRequest;
import br.com.leaf.sintasebemapp.infra.dto.response.UsuarioResponse;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UsuariosControllerIT {

    private MockMvc mockMvc;

    @Mock
    private UsuarioUseCase service;

    private AutoCloseable mock;

    @BeforeEach
    public void setUp() {
        this.mock = MockitoAnnotations.openMocks(this);
        var controller = new UsuariosController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class SalvaUsuario {

        @Test
        void deveSalvarUsuario() throws Exception {

            var response = new UsuarioResponse(UUID.randomUUID());

            var request = new UsuarioRequest("Rafael", "r@r.com", LocalDate.of(2000, 1, 1),
                    "Aa1234567_", "12345678909", "11999999999", "São Paulo", "SP", "Pinheiros", "71910000", "Rua 25 Sul",
                    "123", null, false);

            when(service.salvar(any(Usuario.class))).thenAnswer(i -> response);

            mockMvc.perform(post("/v1/usuarios")
                    .contentType(APPLICATION_JSON)
                    .content(asJsonString(request))
            ).andExpect(status().isCreated());

            verify(service, times(1)).salvar(any(Usuario.class));
        }

        @Test
        void deveGerarExcecao_QuandoPayloadForXML() throws Exception {

            var xml = "<usuarioRequest>\n" +
                    "    <nome>Rafael</nome>\n" +
                    "    <email>r@R.com</email>\n" +
                    "    <dataNascimento>2000-01-01</dataNascimento>\n" +
                    "</usuarioRequest>";

            mockMvc.perform(post("/v1/usuarios")
                    .contentType(APPLICATION_XML)
                    .content(xml)
            ).andExpect(status().isUnsupportedMediaType());

            verify(service, never()).salvar(any(Usuario.class));
        }

    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(obj);
    }

}
