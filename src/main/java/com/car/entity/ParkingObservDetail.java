package com.car.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "parking_observation_detail")
public class ParkingObservDetail {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty(message = "Licence number of the car is required")
	@Size(min = 2, max = 10, message = "The length of licence number must be between 2 and 10 characters.")
	@Column(name = "licence_number")
	private String licenceNumber;

	@NotNull(message = "Street Name is required")
	@Column(name = "street_name")
	private String streetName;

	@Column(name = "recording_datetime")
	private LocalDateTime recordingDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public LocalDateTime getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(LocalDateTime recordingDate) {
		this.recordingDate = recordingDate;
	}

	
}
