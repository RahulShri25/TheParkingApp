package com.car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LicenceNotRegistered extends Exception {


	private static final long serialVersionUID = 1L;

	/**
	 * LicenceNumber Not Regsitered Exception
	 * @param message
	 */
	public LicenceNotRegistered(String message) {
		super(message);
	}

}
