package com.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;
import com.car.exception.LicenceNotRegistered;
import com.car.service.ParkingService;


@RestController
@RequestMapping("/api")
public class ParkingController {

	@Autowired
	private ParkingService parkingService;
	
	
	// To Register new Car/Vehicle using LicenceNumber Unique Value
	@PostMapping("/registercar")
	public ResponseEntity<ParkingDetail> registerCar(@RequestBody ParkingDetail parkingdetail)
			throws CarAlreadyExistException
	{
		parkingdetail = parkingService.registerCar(parkingdetail);
		return new ResponseEntity<ParkingDetail>(parkingdetail, HttpStatus.CREATED);

	}
	
	
	//To Un-register the Car/Vehicle with calculating the total minutes of parking and cost of parking
	@PostMapping("/unregistercar")
	public ResponseEntity<String> unregisterCar(@RequestBody ParkingDetail parkingdetail) throws LicenceNotRegistered	
	{
		String status = parkingService.unregisterCar(parkingdetail.getLicenceNumber());
		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}
	
	
	// To add Vehicle Observation List
	@PostMapping("/addParkingObservdetail")
	public ResponseEntity<List<ParkingObservDetail>> addParkingObservDetail 
	(@RequestBody List<ParkingObservDetail> entityList)
	{
		
		entityList = (List<ParkingObservDetail>) parkingService.addParkingObservDetail(entityList);
		return new ResponseEntity<List<ParkingObservDetail>>(entityList, HttpStatus.CREATED);
		
		
	}	
	
	//To Get the List of Unregister Car list while Process Observation List.
	@GetMapping("/UnregisterCarReport")
	public ResponseEntity<List<UnRegisterCarReport>> getlistUnregisterCar()
	{
		return new ResponseEntity<>(parkingService.getlistUnregisterCar(), HttpStatus.OK);
		
		
	}
	
	

}
