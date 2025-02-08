package com.desafio.itau.petlocation.infrastructure.adapter.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.itau.petlocation.application.port.in.PetLocationService;
import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;

@RestController
@RequestMapping("/pet-location")
public class PetLocationController {

    private static final Logger logger = LoggerFactory.getLogger("com.desafio.itau.petlocation.utils.logs");

    private final PetLocationService petLocationService;

    public PetLocationController(PetLocationService petLocationService) {
        this.petLocationService = petLocationService;
    }

    @PostMapping("/address")
    public ResponseEntity<Address> getAddress(@RequestBody PetLocation location) {
        logger.info("Recebida solicitação para obter endereço com coordenadas: latitude={}, longitude={}", location.getLatitude(), location.getLongitude());

        if (location.getLatitude() < -90 || location.getLatitude() > 90 || location.getLongitude() < -180 || location.getLongitude() > 180) {
            logger.error("Coordenadas inválidas: latitude={}, longitude={}", location.getLatitude(), location.getLongitude());
            throw new IllegalArgumentException("Coordenadas inválidas: latitude deve estar entre -90 e 90 e longitude entre -180 e 180.");
        }

        Address address = petLocationService.getAddressFromCoordinates(location);
        logger.info("Endereço obtido com sucesso: {}", address);

        return ResponseEntity.ok(address);
    }
}