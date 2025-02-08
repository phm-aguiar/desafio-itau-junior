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
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.desafio.itau.petlocation.application.port.in.PetLocationService;
import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;
import com.desafio.itau.petlocation.infrastructure.adapter.in.PetLocationController;
import com.desafio.itau.petlocation.infrastructure.config.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PetLocationController.class)
@Import({PetLocationControllerTest.TestConfig.class, GlobalExceptionHandler.class}) // Importa o mock e o manipulador global de exceções
public class PetLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetLocationService petLocationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.reset(petLocationService); // Reseta os mocks antes de cada teste
    }

    @Test
    public void testGetAddress_Success() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -73.9729691, LocalDateTime.now());
        Address expectedAddress = new Address("United States", "New York", "New York County", "New York", "5th Avenue");

        Mockito.when(petLocationService.getAddressFromCoordinates(Mockito.any()))
                .thenReturn(expectedAddress);

        // Act
        MvcResult result = mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isOk())
                .andReturn();

        // Print response for debugging
        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + jsonResponse);

        // Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("United States"))
                .andExpect(jsonPath("$.state").value("New York"))
                .andExpect(jsonPath("$.city").value("New York County"))
                .andExpect(jsonPath("$.neighbourhood").value("New York"))
                .andExpect(jsonPath("$.street").value("5th Avenue"));
    }

    @Test
    public void testGetAddress_InvalidCoordinates() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 100.0, 200.0, LocalDateTime.now());

        Mockito.when(petLocationService.getAddressFromCoordinates(location))
                .thenThrow(new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));

        // Act
        MvcResult result = mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Print the response content
        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    @Test
    public void testGetAddress_LatitudeTooLow() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", -91.0, -73.9729691, LocalDateTime.now());

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    @Test
    public void testGetAddress_LatitudeTooHigh() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 91.0, -73.9729691, LocalDateTime.now());

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    @Test
    public void testGetAddress_LongitudeTooLow() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -181.0, LocalDateTime.now());

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    @Test
    public void testGetAddress_LongitudeTooHigh() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, 181.0, LocalDateTime.now());

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    @Test
    public void testGetAddress_LatitudeValid_LongitudeInvalid() throws Exception {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.0, -181.0, LocalDateTime.now());

        // Act & Assert
        mockMvc.perform(post("/pet-location/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180."));
    }

    // Classe para injetar o mock manualmente
    static class TestConfig {

        @Bean
        public PetLocationService petLocationService() {
            return Mockito.mock(PetLocationService.class);
        }
    }
}
