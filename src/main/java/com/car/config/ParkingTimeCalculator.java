package com.car.config;

import java.time.DayOfWeek;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class ParkingTimeCalculator {
	
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	
	
	public static long calculateParkingMinutes(String startDateTimeString, String endDateTimeString) {

		long minutes = 0;
		LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, formatter);

		LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);

		while (startDateTime.isBefore(endDateTime)) {

			// Check if the current date is not a Sunday

			if (!startDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {

				LocalDateTime startOfDay = startDateTime.toLocalDate().atTime(8, 0);

				LocalDateTime endOfDay = startDateTime.toLocalDate().atTime(21, 0);

				// Check if the time is between 08:00 and 21:00

				if (startDateTime.isAfter(startOfDay) && startDateTime.isBefore(endOfDay)) {

					minutes++;

				}

			}

			// Move to the next minute

			startDateTime = startDateTime.plusMinutes(1);

		}

		return minutes;

	}

	/*public static void main(String[] args) {

		// Example input dates in the specified format

		String startDateTimeString = "2024-02-10T14:30:50";

		String endDateTimeString = "2024-02-12T14:30:50";

		// Parse the input strings into LocalDateTime objects


		long parkingMinutes = calculateParkingMinutes(startDateTimeString, endDateTimeString);

		System.out.println("Total parking minutes: " + parkingMinutes);

	}*/

}
