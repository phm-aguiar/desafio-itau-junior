package com.desafio.itau.petlocation;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.desafio.itau.petlocation.application.port.out.GeolocationService;
import com.desafio.itau.petlocation.application.service.PetLocationServiceImpl;
import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;

public class PetLocationServiceImplTest {

    @Mock
    private GeolocationService geolocationService;

    @InjectMocks
    private PetLocationServiceImpl petLocationServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAddressFromCoordinates_InvalidCoordinates() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 100.0, 200.0, LocalDateTime.now());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petLocationServiceImpl.getAddressFromCoordinates(location);
        });
        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    public void testGetAddressFromCoordinates_NullResponse() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -73.9729691, LocalDateTime.now());

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(null);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertNull(actualAddress);

        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_EmptyResponse() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -73.9729691, LocalDateTime.now());

        Address emptyAddress = new Address("", "", "", "", "");

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(emptyAddress);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertEquals(emptyAddress.getCountry(), actualAddress.getCountry());
        assertEquals(emptyAddress.getState(), actualAddress.getState());
        assertEquals(emptyAddress.getCity(), actualAddress.getCity());
        assertEquals(emptyAddress.getNeighbourhood(), actualAddress.getNeighbourhood());
        assertEquals(emptyAddress.getStreet(), actualAddress.getStreet());

        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_LatitudeBoundary() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 90.0, -73.9729691, LocalDateTime.now());

        Address expectedAddress = new Address(
                "USA",
                "New York",
                "New York County",
                "New York",
                "5th Avenue"
        );

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(expectedAddress);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertEquals(expectedAddress.getCountry(), actualAddress.getCountry());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getNeighbourhood(), actualAddress.getNeighbourhood());
        assertEquals(expectedAddress.getStreet(), actualAddress.getStreet());

        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_LongitudeBoundary() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, 180.0, LocalDateTime.now());

        Address expectedAddress = new Address(
                "USA",
                "New York",
                "New York County",
                "New York",
                "5th Avenue"
        );

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(expectedAddress);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertEquals(expectedAddress.getCountry(), actualAddress.getCountry());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getNeighbourhood(), actualAddress.getNeighbourhood());
        assertEquals(expectedAddress.getStreet(), actualAddress.getStreet());

        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_LatitudeOutOfBounds() {
        // Arrange
        PetLocation location = new PetLocation("PetName", -91.0, -73.9729691, LocalDateTime.now());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petLocationServiceImpl.getAddressFromCoordinates(location);
        });
        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    public void testGetAddressFromCoordinates_LongitudeOutOfBounds() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, 181.0, LocalDateTime.now());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petLocationServiceImpl.getAddressFromCoordinates(location);
        });
        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    public void testGetAddressFromCoordinates_BothCoordinatesOutOfBounds() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 91.0, 181.0, LocalDateTime.now());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petLocationServiceImpl.getAddressFromCoordinates(location);
        });
        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    public void testGetAddressFromCoordinates_LatitudeLowerBoundary_LongitudeUpperBoundary() {
        // Arrange
        PetLocation location = new PetLocation("PetName", -90.0, 180.0, LocalDateTime.now());

        Address expectedAddress = new Address(
                "USA",
                "New York",
                "New York County",
                "New York",
                "5th Avenue"
        );

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(expectedAddress);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertEquals(expectedAddress.getCountry(), actualAddress.getCountry());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getNeighbourhood(), actualAddress.getNeighbourhood());
        assertEquals(expectedAddress.getStreet(), actualAddress.getStreet());

        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_Success() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -73.9729691, LocalDateTime.now());

        Address expectedAddress = new Address(
                "USA",
                "New York",
                "New York County",
                "New York",
                "5th Avenue"
        );

        when(geolocationService.getAddress(location.getLatitude(), location.getLongitude())).thenReturn(expectedAddress);

        // Act
        Address actualAddress = petLocationServiceImpl.getAddressFromCoordinates(location);

        // Assert
        assertEquals(expectedAddress.getCountry(), actualAddress.getCountry());
        assertEquals(expectedAddress.getState(), actualAddress.getState());
        assertEquals(expectedAddress.getCity(), actualAddress.getCity());
        assertEquals(expectedAddress.getNeighbourhood(), actualAddress.getNeighbourhood());
        assertEquals(expectedAddress.getStreet(), actualAddress.getStreet());

        // Verifica se o método do mock foi chamado corretamente
        verify(geolocationService).getAddress(location.getLatitude(), location.getLongitude());
    }

    @Test
    public void testGetAddressFromCoordinates_LongitudeTooLow() {
        // Arrange
        PetLocation location = new PetLocation("PetName", 40.7638435, -181.0, LocalDateTime.now());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petLocationServiceImpl.getAddressFromCoordinates(location);
        });
        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

}
