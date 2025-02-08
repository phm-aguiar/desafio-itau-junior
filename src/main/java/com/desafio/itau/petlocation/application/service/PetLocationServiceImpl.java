package com.desafio.itau.petlocation.application.service;

import org.springframework.stereotype.Service;

import com.desafio.itau.petlocation.application.port.in.PetLocationService;
import com.desafio.itau.petlocation.application.port.out.GeolocationService;
import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;

@Service
public class PetLocationServiceImpl implements PetLocationService {

    private final GeolocationService geolocationService;

    public PetLocationServiceImpl(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @Override
    public Address getAddressFromCoordinates(PetLocation location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.");
        }

        return geolocationService.getAddress(latitude, longitude);
    }
}