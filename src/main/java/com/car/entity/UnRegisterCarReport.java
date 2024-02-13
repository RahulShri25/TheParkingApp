package com.car.entity;

import java.time.LocalDateTime;

public class UnRegisterCarReport {

	private String licenceNumber;
	private String streetName;
	private LocalDateTime reportingDate;

	public UnRegisterCarReport(String licenceNumber, String streetName, LocalDateTime reportingDate) {
		super();
		this.licenceNumber = licenceNumber;
		this.streetName = streetName;
		this.reportingDate = reportingDate;
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

	public LocalDateTime getReportingDate() {
		return reportingDate;
	}

	public void setReportingDate(LocalDateTime reportingDate) {
		this.reportingDate = reportingDate;
	}

}
