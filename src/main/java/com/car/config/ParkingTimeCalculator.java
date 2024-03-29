package com.car.config;

import java.time.DayOfWeek;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

/**
 * 
 * @author rahul.shrivastava
 *
 */
public class ParkingTimeCalculator {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * To Calculate The Parking Cost Based on number of minutes of parking and timing constraints.
	 *  Mon-Fri (08:00 to 21:00) and Excludes Sunday.
	 * @param startDateTimeString
	 * @param endDateTimeString
	 * @return
	 */
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

}
