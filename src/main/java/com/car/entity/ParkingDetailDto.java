package com.car.entity;

import java.time.LocalDateTime;

import com.car.config.Constant;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class ParkingDetailDto {

	@NotEmpty(message = "Licence number of the car is required")
	@Size(min = 2, max = 10, message = "The length of licence number must be between 2 and 10 characters.")
	private String licenceNumber;

	@NotNull(message = "Street Name is required")
	private String streetName;
	
	public ParkingDetail toparkingdetail() {
		return null;
		
		/*return new ParkingDetail().setLicenceNumber(licenceNumber)
				.setStreetName(streetName)
				.setArrivalTime(LocalDateTime.now().withNano(0))
				.setDepartureTime(LocalDateTime.now().withNano(0))
				.setCurrentStatus(Constant.Car_Registered);*/
	}
}
