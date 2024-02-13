package com.car.entity;

import java.math.BigDecimal;

public class ParkingResponsedto {
	
	private String message;


	private BigDecimal parkingAmount;
	
	private long parkMinutes;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BigDecimal getParkingAmount() {
		return parkingAmount;
	}

	public void setParkingAmount(BigDecimal parkingAmount) {
		this.parkingAmount = parkingAmount;
	}

	public long getParkMinutes() {
		return parkMinutes;
	}

	public void setParkMinutes(long parkMinutes) {
		this.parkMinutes = parkMinutes;
	}

	public ParkingResponsedto(String message, BigDecimal parkingAmount, long parkMinutes) {
		super();
		this.message = message;
		this.parkingAmount = parkingAmount;
		this.parkMinutes = parkMinutes;
	}

}
