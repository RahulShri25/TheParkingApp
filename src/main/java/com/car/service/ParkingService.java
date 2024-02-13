package com.car.service;

import java.util.List;

import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.ParkingResponsedto;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;
import com.car.exception.LicenceNotRegistered;

public interface ParkingService {


	/**
	 * To Register the car/vehicle based on License Number and Street Name
	 * @param parkingdetail
	 * @return
	 * @throws CarAlreadyExistException
	 */
	public ParkingDetail registerCar(ParkingDetail parkingdetail) throws CarAlreadyExistException;


	/**
	 * To Un-Register the car/vehicle based on License Number and Street Name
	 * @param licenceNumber
	 * @return
	 * @throws LicenceNotRegistered
	 */
	public ParkingResponsedto unregisterCar(String licenceNumber) throws LicenceNotRegistered;


	/**
	 * To Add Car Monitoring List data
	 * @param entityList
	 * @return
	 */
	public List<ParkingObservDetail> addParkingObservDetail(List<ParkingObservDetail> entityList);


	/**
	 * To Get The Report to unregistered car/vehicle
	 * @return
	 */
	public List<UnRegisterCarReport> getlistUnregisterCar();
}
