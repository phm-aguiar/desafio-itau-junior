package com.desafio.itau.petlocation;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.desafio.itau.petlocation.application.port.in.PetLocationService;
import com.desafio.itau.petlocation.domain.model.PetLocation;
import com.desafio.itau.petlocation.infrastructure.adapter.in.PetLocationController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PetLocationController.class)
@Import(GlobalExceptionHandlerTest.TestConfig.class) // Importa a configuração do mock manualmente
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetLocationService petLocationService; // Agora injetado via @Import

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.reset(petLocationService);
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 100.0, 200.0, LocalDateTime.now());

        Mockito.when(petLocationService.getAddressFromCoordinates(location))
                .thenThrow(new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."))
                .andExpect(jsonPath("$.path").value("/pet-location/address"));
    }

    // Classe de configuração para fornecer o mock de PetLocationService
    static class TestConfig {
        @Bean
        public PetLocationService petLocationService() {
            return Mockito.mock(PetLocationService.class);
        }
    }
}
