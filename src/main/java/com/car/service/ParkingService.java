package com.car.service;

import java.util.List;

import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;

public interface ParkingService {

	
	public ParkingDetail registerCar(ParkingDetail parkingdetail) throws CarAlreadyExistException;
	
	public String unregisterCar(String licenceNumber);
	
	public List<ParkingObservDetail> addParkingObservDetail(List<ParkingObservDetail> entityList);

	public  List<UnRegisterCarReport> getlistUnregisterCar();
}
