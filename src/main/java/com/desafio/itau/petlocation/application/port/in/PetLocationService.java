package com.desafio.itau.petlocation.application.port.in;

import com.desafio.itau.petlocation.domain.model.Address;
import com.desafio.itau.petlocation.domain.model.PetLocation;

public interface PetLocationService {
	Address getAddressFromCoordinates(PetLocation location);
}
