package com.desafio.itau.petlocation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.infrastructure.adapter.out.PositionStackAdapter;
import com.desafio.itau.petlocation.infrastructure.adapter.out.PositionStackResponse;

class PositionStackAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PositionStackAdapter positionStackAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(positionStackAdapter, "apiUrl", "https://api.positionstack.com/v1/reverse");
        ReflectionTestUtils.setField(positionStackAdapter, "apiKey", "test_key");
    }

    @Test
    void testGetAddress_Success() {
        // Arrange
        double latitude = 40.7638435; 
        double longitude = -73.9729691; 

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        PositionStackResponse.Data data = new PositionStackResponse.Data();
        data.country = "United States";
        data.region = "New York";
        data.locality = "Manhattan";
        data.county = "New York County";
        data.street = "5th Avenue";
        response.data = new PositionStackResponse.Data[]{data};

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNotNull(address);
        assertEquals("United States", address.getCountry());
        assertEquals("New York", address.getState());
        assertEquals("Manhattan", address.getCity());
        assertEquals("New York County", address.getNeighbourhood());
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void testGetAddress_InvalidLatitude() {
        // Arrange
        double latitude = 100.0; 
        double longitude = -73.9729691;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    void testGetAddress_InvalidLongitude() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = -200.0; 

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    void testGetAddress_NullResponse() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = -73.9729691;

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(null);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNull(address);
    }

    @Test
    void testGetAddress_EmptyData() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = -73.9729691;

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        response.data = new PositionStackResponse.Data[0]; // Data vazio

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNull(address);
    }

    @Test
    void testGetAddress_NullData() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = -73.9729691;

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        response.data = null; 

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNull(address);
    }

    @Test
    void testGetAddress_LatitudeLowerBound() {
        // Arrange
        double latitude = -90.0;
        double longitude = -73.9729691;

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        PositionStackResponse.Data data = new PositionStackResponse.Data();
        data.country = "United States";
        data.region = "New York";
        data.locality = "Manhattan";
        data.county = "New York County";
        data.street = "5th Avenue";
        response.data = new PositionStackResponse.Data[]{data};

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNotNull(address);
        assertEquals("United States", address.getCountry());
        assertEquals("New York", address.getState());
        assertEquals("Manhattan", address.getCity());
        assertEquals("New York County", address.getNeighbourhood());
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void testGetAddress_LatitudeUpperBound() {
        // Arrange
        double latitude = 90.0;
        double longitude = -73.9729691;

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        PositionStackResponse.Data data = new PositionStackResponse.Data();
        data.country = "United States";
        data.region = "New York";
        data.locality = "Manhattan";
        data.county = "New York County";
        data.street = "5th Avenue";
        response.data = new PositionStackResponse.Data[]{data};

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNotNull(address);
        assertEquals("United States", address.getCountry());
        assertEquals("New York", address.getState());
        assertEquals("Manhattan", address.getCity());
        assertEquals("New York County", address.getNeighbourhood());
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void testGetAddress_LongitudeLowerBound() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = -180.0; 

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        PositionStackResponse.Data data = new PositionStackResponse.Data();
        data.country = "United States";
        data.region = "New York";
        data.locality = "Manhattan";
        data.county = "New York County";
        data.street = "5th Avenue";
        response.data = new PositionStackResponse.Data[]{data};

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNotNull(address);
        assertEquals("United States", address.getCountry());
        assertEquals("New York", address.getState());
        assertEquals("Manhattan", address.getCity());
        assertEquals("New York County", address.getNeighbourhood());
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void testGetAddress_LongitudeUpperBound() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = 180.0; 

        String apiUrl = ReflectionTestUtils.getField(positionStackAdapter, "apiUrl").toString();
        String apiKey = ReflectionTestUtils.getField(positionStackAdapter, "apiKey").toString();

        String expectedUrl = String.format("%s?access_key=%s&query=%s,%s", apiUrl, apiKey, latitude, longitude);

        PositionStackResponse response = new PositionStackResponse();
        PositionStackResponse.Data data = new PositionStackResponse.Data();
        data.country = "United States";
        data.region = "New York";
        data.locality = "Manhattan";
        data.county = "New York County";
        data.street = "5th Avenue";
        response.data = new PositionStackResponse.Data[]{data};

        when(restTemplate.getForObject(eq(expectedUrl), eq(PositionStackResponse.class)))
                .thenReturn(response);

        // Act
        Address address = positionStackAdapter.getAddress(latitude, longitude);

        // Assert
        assertNotNull(address);
        assertEquals("United States", address.getCountry());
        assertEquals("New York", address.getState());
        assertEquals("Manhattan", address.getCity());
        assertEquals("New York County", address.getNeighbourhood());
        assertEquals("5th Avenue", address.getStreet());
    }

    @Test
    void testGetAddress_LatitudeValid_LongitudeInvalid() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = 200.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    void testGetAddress_LatitudeValid_LongitudeTooHigh() {
        // Arrange
        double latitude = 40.7638435;
        double longitude = 200.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    void testGetAddress_LatitudeAndLongitudeOutOfBounds() {
        // Arrange
        double latitude = 100.0;
        double longitude = 200.0; 

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

    @Test
    void testGetAddress_LatitudeOutOfBounds_LongitudeAtLowerBound() {
        // Arrange
        double latitude = -91.0;
        double longitude = -180.0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            positionStackAdapter.getAddress(latitude, longitude);
        });

        assertEquals("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.", exception.getMessage());
    }

}
