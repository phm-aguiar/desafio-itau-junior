package com.desafio.itau.petlocation.domain.model;

import java.time.LocalDateTime;

public class PetLocation {
	private String sensorId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    public PetLocation(String sensorId, double latitude, double longitude, LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
