package com.desafio.itau.petlocation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.desafio.itau.petlocation.domain.model.PetLocation;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetLocationTest {

    private PetLocation petLocation;
    private final String sensorId = "Sensor123";
    private final double latitude = 40.7638435;
    private final double longitude = -73.9729691;
    private final LocalDateTime timestamp = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
        petLocation = new PetLocation(sensorId, latitude, longitude, timestamp);
    }

    @Test
    public void testGetSensorId() {
        assertEquals(sensorId, petLocation.getSensorId());
    }

    @Test
    public void testSetSensorId() {
        String newSensorId = "Sensor456";
        petLocation.setSensorId(newSensorId);
        assertEquals(newSensorId, petLocation.getSensorId());
    }

    @Test
    public void testGetLatitude() {
        assertEquals(latitude, petLocation.getLatitude());
    }

    @Test
    public void testSetLatitude() {
        double newLatitude = 41.0;
        petLocation.setLatitude(newLatitude);
        assertEquals(newLatitude, petLocation.getLatitude());
    }

    @Test
    public void testGetLongitude() {
        assertEquals(longitude, petLocation.getLongitude());
    }

    @Test
    public void testSetLongitude() {
        double newLongitude = -74.0;
        petLocation.setLongitude(newLongitude);
        assertEquals(newLongitude, petLocation.getLongitude());
    }

    @Test
    public void testGetTimestamp() {
        assertEquals(timestamp, petLocation.getTimestamp());
    }

    @Test
    public void testSetTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.now().plusDays(1);
        petLocation.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, petLocation.getTimestamp());
    }
}