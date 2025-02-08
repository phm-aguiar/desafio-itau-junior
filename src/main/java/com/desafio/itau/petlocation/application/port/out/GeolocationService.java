package com.desafio.itau.petlocation.application.port.out;

import com.desafio.itau.petlocation.domain.model.Address;

public interface GeolocationService {
	Address getAddress(double latitude, double longitude);
}
