package com.desafio.itau.petlocation.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.desafio.itau.petlocation.application.port.in.PetLocationService;
import com.desafio.itau.petlocation.application.port.out.GeolocationService;
import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;

@Service
public class PetLocationServiceImpl implements PetLocationService {

    private static final Logger logger = LoggerFactory.getLogger("com.desafio.itau.petlocation.utils.logs");

    private final GeolocationService geolocationService;

    public PetLocationServiceImpl(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @Override
    public Address getAddressFromCoordinates(PetLocation location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        logger.info("Obtendo endereço para coordenadas: latitude={}, longitude={}", latitude, longitude);

        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            logger.error("Coordenadas inválidas: latitude={}, longitude={}", latitude, longitude);
            throw new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.");
        }

        Address address = geolocationService.getAddress(latitude, longitude);
        logger.info("Endereço obtido com sucesso: {}", address);

        return address;
    }
}
