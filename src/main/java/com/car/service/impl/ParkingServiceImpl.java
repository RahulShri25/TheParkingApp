package com.car.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.config.Constant;
import com.car.config.ParkingTimeCalculator;
import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.ParkingResponsedto;
import com.car.entity.PriceDetail;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;
import com.car.exception.LicenceNotRegistered;
import com.car.respository.ParkingCostRepository;
import com.car.respository.ParkingObservationDetail;
import com.car.respository.ParkingRepository;
import com.car.service.ParkingService;


@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	private ParkingRepository parkingRepo;
	private ParkingCostRepository parkingCostRepo;
	private ParkingObservationDetail parkingObservRepo;

	public ParkingServiceImpl(ParkingRepository parkingRepo, ParkingCostRepository parkingCostRepo,
			ParkingObservationDetail parkingobservRepo) {
		super();
		this.parkingRepo = parkingRepo;
		this.parkingCostRepo = parkingCostRepo;
		this.parkingObservRepo = parkingObservRepo;

	}
	
	
	/**
	 * To Get The car Register based on License Number and Street Name
	 */

	@Override
	public ParkingDetail registerCar(ParkingDetail parkingdetail) throws CarAlreadyExistException {
		var alreadyRegistered = parkingRepo.findByLicenceNumber(parkingdetail.getLicenceNumber());
		if (alreadyRegistered.isPresent()) {
			throw new CarAlreadyExistException("Car is Already Registered");
		}
		parkingdetail.setArrivalTime(LocalDateTime.now().withNano(0));
		parkingdetail.setCurrentStatus(Constant.Car_Registered);
		return parkingRepo.save(parkingdetail);

	}

	
	/**
	 * To Get The car Un-Register based on License Number
	 */
	@Override
	public ParkingResponsedto unregisterCar(String licenceNumber) throws LicenceNotRegistered {

		Optional<ParkingDetail> pd = parkingRepo.findByLicenceNumberAndCurrentStatus(licenceNumber,
				Constant.Car_Registered);
		if (pd.isEmpty()) {

			throw new LicenceNotRegistered("Parking registration is not found for licence number :" + " "
					+ licenceNumber + " Or already De-registration");
		}

		var streetCost = parkingCostRepo.findAll().stream()
				.collect(Collectors.toMap(PriceDetail::getStreetName, PriceDetail::getPrice));
		return this.calculateParkingCost(pd.get(), streetCost);
	}


	/**
	 * To Calculate The Parking Cost Based on number of minutes of parking and  timing constraints.
	 *  Mon-Fri (08:00 to 21:00) and Excludes Sunday.
	 * @param parkDetail
	 * @param prices
	 * @return ParkingResponsedto
	 */
	private ParkingResponsedto calculateParkingCost(ParkingDetail parkDetail, Map<String, BigDecimal> prices) {

		parkDetail.setCurrentStatus(Constant.Car_UnRegistered);
		parkDetail.setDepartureTime(LocalDateTime.now().withNano(0));
		var newDetail = parkingRepo.save(parkDetail);

		Long minutes = ParkingTimeCalculator.calculateParkingMinutes(newDetail.getArrivalTime().toString(),
				newDetail.getDepartureTime().toString());

		var parkingAmount = prices.get(newDetail.getStreetName()).multiply(BigDecimal.valueOf(minutes));
		
		return new ParkingResponsedto( "You have successfully De-Registered you vehicle.", parkingAmount.divide(BigDecimal.valueOf(100)), minutes);

	}


	/**
	 * This is add Parking Monitoring list data into database
	 */
	@Override
	public List<ParkingObservDetail> addParkingObservDetail(List<ParkingObservDetail> entityList) {
		return parkingObservRepo.saveAll(entityList);
	}


    /**
     *  This is to generate the Report of Un-registered car/vehicles
     */
	@Override
	public List<UnRegisterCarReport> getlistUnregisterCar() {

		LocalDate date = LocalDateTime.now().toLocalDate();
		// All Parked vehicle details for the day
		var parkDetails = parkingRepo.findByArrivalTimeBetween(LocalDateTime.of(date, LocalTime.MIN),
				LocalDateTime.of(date, LocalTime.MAX));
		// All monitoring details of parked vehicle.
		var observDetails = parkingObservRepo.findByRecordingDateBetween(LocalDateTime.of(date, LocalTime.MIN),
				LocalDateTime.of(date, LocalTime.MAX));

		List<ParkingObservDetail> finalData = new ArrayList<>();

		observDetails.forEach(item -> {
			if (!parkDetails.stream().map(m -> m.getLicenceNumber()).toList().contains(item.getLicenceNumber())) {
				finalData.add(item);
			}
		});

		finalData.addAll(observDetails.stream()
				.filter(o -> parkDetails.stream()
						.anyMatch(pd -> o.getLicenceNumber().equals(pd.getLicenceNumber())
								&& o.getStreetName().equals(pd.getStreetName())
								&& (o.getRecordingDate().isAfter(pd.getDepartureTime())
										|| o.getRecordingDate().isBefore(pd.getArrivalTime()))))
				.collect(Collectors.toList()));

		return finalData.stream().map(
				item -> new UnRegisterCarReport(item.getLicenceNumber(), item.getStreetName(), item.getRecordingDate()))
				.collect(Collectors.toList());
	}

}
