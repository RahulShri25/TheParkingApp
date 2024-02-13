package com.car.exception;

public class CarAlreadyExistException extends Exception {


	private static final long serialVersionUID = 1L;
	/**
	 * Exception If car is alreadyRegistered
	 * @param msg
	 */
	public CarAlreadyExistException(String msg) {
		super(msg);
	}

}
