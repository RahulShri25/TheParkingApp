package com.car.service;

import java.util.List;

import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;
import com.car.exception.LicenceNotRegistered;

public interface ParkingService {

	// To Register the car/vehicle based on License Number and Street Name
	public ParkingDetail registerCar(ParkingDetail parkingdetail) throws CarAlreadyExistException;
	
	// To Un-Register the car/vehicle based on License Number and Street Name
	public String unregisterCar(String licenceNumber) throws LicenceNotRegistered;
	
	//To Add Car Monitoring List data
	public List<ParkingObservDetail> addParkingObservDetail(List<ParkingObservDetail> entityList);

	//To Get The Report to unregistered car/vehicle
	public  List<UnRegisterCarReport> getlistUnregisterCar();
}
