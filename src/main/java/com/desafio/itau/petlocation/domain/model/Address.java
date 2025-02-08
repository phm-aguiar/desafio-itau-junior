package com.desafio.itau.petlocation.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("neighbourhood")
    private String neighbourhood;
    @JsonProperty("street")
    private String street;

    public Address(String country, String state, String city, String neighbourhood, String street) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.neighbourhood = neighbourhood;
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
