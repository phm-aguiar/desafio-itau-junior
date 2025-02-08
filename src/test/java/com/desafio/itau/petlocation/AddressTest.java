package com.desafio.itau.petlocation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.desafio.itau.petlocation.domain.model.Address;

public class AddressTest {

    @Test
    public void testAddressGettersAndSetters() {
        // Arrange
        String country = "USA";
        String state = "New York";
        String city = "New York City";
        String neighbourhood = "Manhattan";
        String street = "5th Avenue";

        // Act
        Address address = new Address(country, state, city, neighbourhood, street);

        // Assert
        assertEquals(country, address.getCountry());
        assertEquals(state, address.getState());
        assertEquals(city, address.getCity());
        assertEquals(neighbourhood, address.getNeighbourhood());
        assertEquals(street, address.getStreet());

        // Test setters
        String newCountry = "Canada";
        String newState = "Ontario";
        String newCity = "Toronto";
        String newNeighbourhood = "Downtown";
        String newStreet = "Queen Street";

        address.setCountry(newCountry);
        address.setState(newState);
        address.setCity(newCity);
        address.setNeighbourhood(newNeighbourhood);
        address.setStreet(newStreet);

        assertEquals(newCountry, address.getCountry());
        assertEquals(newState, address.getState());
        assertEquals(newCity, address.getCity());
        assertEquals(newNeighbourhood, address.getNeighbourhood());
        assertEquals(newStreet, address.getStreet());
    }
}