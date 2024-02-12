package com.car.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.PriceDetail;
import com.car.entity.UnRegisterCarReport;
import com.car.exception.CarAlreadyExistException;
import com.car.exception.LicenceNotRegistered;
import com.car.respository.ParkingCostRepository;
import com.car.respository.ParkingObservationDetail;
import com.car.respository.ParkingRepository;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
	@Mock
	private ParkingRepository parkingRepo;

	@Mock
	private ParkingCostRepository parkingCostRepo;
	
	@Mock
	private ParkingObservationDetail parkingobsvRepo;
	
	
	@InjectMocks
	private ParkingService parkingService;
	
	static List<PriceDetail> priceDetail;
	
	@BeforeAll
	static void beforeAll() {
		PriceDetail pd1 = new PriceDetail();
		pd1.setPrice(BigDecimal.valueOf(3));
		pd1.setStreetName("Java");
		
		PriceDetail pd2 = new PriceDetail();
		pd1.setPrice(BigDecimal.valueOf(3));
		pd1.setStreetName("Jakarta");
		
		PriceDetail pd3 = new PriceDetail();
		pd1.setPrice(BigDecimal.valueOf(3));
		pd1.setStreetName("Spring");
		
		PriceDetail pd4 = new PriceDetail();
		pd1.setPrice(BigDecimal.valueOf(3));
		pd1.setStreetName("Azure");
		
		priceDetail = List.of(pd1,pd2,pd3,pd4);

	}
	
	
	
	@Test
	void registercar() throws CarAlreadyExistException {
		ParkingDetail parkd= new ParkingDetail();
		parkd.setLicenceNumber("abc102");
		parkd.setStreetName("Java");
		
		when(parkingRepo.save(any(ParkingDetail.class))).thenReturn(parkd);
		
		ParkingDetail pd = parkingService.registerCar(parkd);
		Assertions.assertEquals(pd.getLicenceNumber(),"abc102");
		Assertions.assertEquals(pd.getStreetName(),"Java");
	}
	

	@Test
	void registercarWithException() throws CarAlreadyExistException {
		ParkingDetail parkd= new ParkingDetail();
		parkd.setLicenceNumber("abc102");
		parkd.setStreetName("Java");
		
		when(parkingRepo.findByLicenceNumberAndCurrentStatus(any(), any())).thenReturn(Optional.of(parkd));
		
		assertThrows(CarAlreadyExistException.class,
				()-> when(parkingService.registerCar(parkd)).thenThrow(new CarAlreadyExistException("")));
	}
	
	@Test
	void unRegisterCar() throws LicenceNotRegistered{
		
		ParkingDetail parkd= new ParkingDetail();
		parkd.setLicenceNumber("abc102");
		parkd.setStreetName("Java");
		parkd.setCurrentStatus("DeParked");
		parkd.setDepartureTime(LocalDateTime.now().withNano(0));
		
		Mockito.<Optional<ParkingDetail>>when(parkingRepo.findByLicenceNumberAndCurrentStatus(any(), any()))
		.thenReturn(Optional.of(parkd));
		when(parkingCostRepo.findAll()).thenReturn(priceDetail);
		String result = parkingService.unregisterCar("abc102");
		Assertions.assertEquals(result,"You have successfully De-Registered your vehicle");
	}
	
	
	@Test
	void addparkingObservDetailsTest() {
		ParkingObservDetail pod1 = new ParkingObservDetail();
		pod1.setLicenceNumber("abc107");
		pod1.setStreetName("Jakarta");
		pod1.setRecordingDate(LocalDateTime.now().withNano(0));
		
		ParkingObservDetail pod2 = new ParkingObservDetail();
		pod2.setLicenceNumber("abc108");
		pod2.setStreetName("Java");
		pod2.setRecordingDate(LocalDateTime.now().withNano(0));
		when(parkingobsvRepo.saveAll(any())).thenReturn(List.of(pod1,pod2));
		
		List<ParkingObservDetail> poDetail = parkingService.addParkingObservDetail(List.of(pod1,pod2));
		assertEquals(poDetail.size(),2);
	}
	
	
	@Test
	void listUnregisterCar() {
		ParkingObservDetail pod1 = new ParkingObservDetail();
		pod1.setLicenceNumber("abc107");
		pod1.setStreetName("Jakarta");
		pod1.setRecordingDate(LocalDateTime.now().withNano(0));
		
		ParkingObservDetail pod2 = new ParkingObservDetail();
		pod2.setLicenceNumber("abc108");
		pod2.setStreetName("Java");
		pod2.setRecordingDate(LocalDateTime.now().withNano(0));
		
		ParkingDetail parkd01= new ParkingDetail();
		parkd01.setLicenceNumber("abc102");
		parkd01.setStreetName("Java");
		parkd01.setCurrentStatus("DeParked");
		parkd01.setDepartureTime(LocalDateTime.now().withNano(0));
		
		ParkingDetail parkd02= new ParkingDetail();
		parkd02.setLicenceNumber("abc102");
		parkd02.setStreetName("Java");
		parkd02.setCurrentStatus("DeParked");
		parkd02.setDepartureTime(LocalDateTime.now().withNano(0));
		
		when(parkingobsvRepo.findByRecordingDateBetween(any(),any())).thenReturn(List.of(pod1,pod2));
		when(parkingRepo.findByArrivalTimeBetween(any(),any())).thenReturn(List.of(parkd01,parkd02));
		List<UnRegisterCarReport> reportDetails = parkingService.getlistUnregisterCar();
		assertEquals(reportDetails.size(), 2);
	}
	
}
