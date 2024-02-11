package com.car.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnRegisterCarReport {
	
	private String licenceNumber;
	private String streetName;
	private LocalDateTime date;
	
	public UnRegisterCarReport(String licenceNumber2, String streetName2, LocalDateTime recordingDate) {
		// TODO Auto-generated constructor stub
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
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
