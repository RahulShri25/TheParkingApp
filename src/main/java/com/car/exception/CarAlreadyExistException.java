package com.car.exception;

public class CarAlreadyExistException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public CarAlreadyExistException(String msg) {
		super(msg);
	}

}
