package com.desafio.itau.petlocation.infrastructure.adapter.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.desafio.itau.petlocation.application.port.out.GeolocationService;
import com.desafio.itau.petlocation.domain.model.Address;

@Service
public class PositionStackAdapter implements GeolocationService {

    private final RestTemplate restTemplate;

    @Value("${positionstack.api.url}")
    private String apiUrl;

    @Value("${positionstack.api.key}")
    private String apiKey;

    public PositionStackAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Address getAddress(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.");
        }
        String query = latitude + "," + longitude;
        String url = String.format("%s?access_key=%s&query=%s", apiUrl, apiKey, query);

        PositionStackResponse response = restTemplate.getForObject(url, PositionStackResponse.class);

        if (response != null && response.data != null && response.data.length > 0) {
            PositionStackResponse.Data result = response.data[0];
            return new Address(
                    result.country, result.region, result.locality, result.county, result.street
            );
        }
        return null;
    }
}