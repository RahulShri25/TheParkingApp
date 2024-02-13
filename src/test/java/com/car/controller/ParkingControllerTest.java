package com.car.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.car.config.Constant;
import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.ParkingObservdto;
import com.car.entity.ParkingResponsedto;
import com.car.entity.UnRegisterCarReport;
import com.car.service.ParkingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ParkingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParkingService parkingService;

	@Test
	void registerCarTest() throws Exception {

		ParkingDetail inputdata = new ParkingDetail();
		inputdata.setLicenceNumber("abc109");
		inputdata.setStreetName("Jakarta");
		inputdata.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		inputdata.setDepartureTime(LocalDateTime.now().withNano(0));
		inputdata.setCurrentStatus(Constant.Car_Registered);

		Mockito.when(parkingService.registerCar(ArgumentMatchers.any())).thenReturn(inputdata);
		mockMvc.perform(post("/api/registercar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(convertJsonString(inputdata)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.licenceNumber", Matchers.equalTo("abc109")))
				.andExpect(jsonPath("$.streetName", Matchers.equalTo("Jakarta")));

	}

	@Test
	void unRegisterCarTest() throws Exception {

		ParkingDetail inputdata = new ParkingDetail();
		inputdata.setLicenceNumber("abc109");
		inputdata.setStreetName("Jakarta");
		inputdata.setCurrentStatus(Constant.Car_UnRegistered);
		inputdata.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		inputdata.setDepartureTime(LocalDateTime.now().withNano(0));

		ParkingDetail val = new ParkingDetail();
		val.setLicenceNumber(inputdata.getLicenceNumber());
		val.setStreetName((inputdata.getStreetName()));
		val.setCurrentStatus((inputdata.getCurrentStatus()));
		val.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		val.setDepartureTime(LocalDateTime.now().withNano(0));

		Mockito.when(parkingService.unregisterCar(ArgumentMatchers.any())).thenReturn(
				new ParkingResponsedto("You have successfully De-Registered you vehicle. ", BigDecimal.ZERO, 5));

		mockMvc.perform(post("/api/unregistercar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(convertJsonString(inputdata)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.parkingAmount", Matchers.equalTo(BigDecimal.ZERO.intValue())));

	}

	@Test
	void unRegisterCarOnSundayTest() throws Exception {

		ParkingDetail inputdata = new ParkingDetail();
		inputdata.setLicenceNumber("abc109");
		inputdata.setStreetName("Jakarta");
		inputdata.setCurrentStatus(Constant.Car_UnRegistered);
		inputdata.setArrivalTime(LocalDateTime.of(2022, 02, 11, 10, 35, 1));
		inputdata.setDepartureTime(LocalDateTime.of(2022, 02, 11, 12, 45, 1));

		ParkingDetail val = new ParkingDetail();
		val.setLicenceNumber(inputdata.getLicenceNumber());
		val.setStreetName((inputdata.getStreetName()));
		val.setCurrentStatus((inputdata.getCurrentStatus()));
		val.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		val.setDepartureTime(LocalDateTime.now().withNano(0));

		Mockito.when(parkingService.unregisterCar(ArgumentMatchers.any())).thenReturn(
				new ParkingResponsedto("You have successfully De-Registered you vehicle. ", BigDecimal.ZERO, 5));

		mockMvc.perform(post("/api/unregistercar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(convertJsonString(inputdata)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.parkingAmount", Matchers.equalTo(BigDecimal.ZERO.intValue())));

	}

	@Test
	void unRegisterCarLicenceNotRegisteredTest() throws Exception {

		ParkingDetail inputdata = new ParkingDetail();
		inputdata.setLicenceNumber("abc109");
		inputdata.setStreetName("Jakarta");
		inputdata.setCurrentStatus(Constant.Car_UnRegistered);
		inputdata.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		inputdata.setDepartureTime(LocalDateTime.now().withNano(0));

		ParkingDetail val = new ParkingDetail();
		val.setLicenceNumber(inputdata.getLicenceNumber());
		val.setStreetName((inputdata.getStreetName()));
		val.setCurrentStatus((inputdata.getCurrentStatus()));
		val.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		val.setDepartureTime(LocalDateTime.now().withNano(0));

		Mockito.when(parkingService.unregisterCar(ArgumentMatchers.any())).thenReturn(
				new ParkingResponsedto("You have successfully De-Registered you vehicle. ", BigDecimal.ZERO, 5));

		mockMvc.perform(post("/api/unregistercar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(convertJsonString(inputdata)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.parkingAmount", Matchers.equalTo(BigDecimal.ZERO.intValue())));

	}

	@Test
	void addParkingObservationDetailTest() throws Exception {

		ParkingObservDetail observ1 = new ParkingObservDetail();
		observ1.setLicenceNumber("abc110");
		observ1.setStreetName("Jakarta");
		observ1.setRecordingDate(LocalDateTime.now().withNano(0));

		ParkingObservDetail observ2 = new ParkingObservDetail();
		observ2.setLicenceNumber("abc111");
		observ2.setStreetName("Java");
		observ2.setRecordingDate(LocalDateTime.now().withNano(0));

		List<ParkingObservDetail> entityList = (List<ParkingObservDetail>) (List.of(observ1, observ2));

		Mockito.when(parkingService.addParkingObservDetail(ArgumentMatchers.any()))
				.thenReturn(List.of(observ1, observ2));
		mockMvc.perform(post("/api/addParkingObservdetail").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(convertJsonString(entityList)).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].licenceNumber", Matchers.equalTo("abc110")))
				.andExpect(jsonPath("$[1].licenceNumber", Matchers.equalTo("abc111")))
				.andExpect(jsonPath("$", Matchers.hasSize(2)));
	}

	@Test
	void fineReportfoUnregisterCarTest() throws Exception {

		UnRegisterCarReport rep01 = new UnRegisterCarReport("abc112", "Java",
				LocalDateTime.now().withNano(0).minusMinutes(20));
		UnRegisterCarReport rep02 = new UnRegisterCarReport("abc113", "Jakarta",
				LocalDateTime.now().withNano(0).minusMinutes(100));
		UnRegisterCarReport rep03 = new UnRegisterCarReport("abc114", "Azure",
				LocalDateTime.now().withNano(0).minusMinutes(400));

		List<UnRegisterCarReport> ulist = List.of(rep01, rep02, rep03);
		Mockito.when(parkingService.getlistUnregisterCar()).thenReturn(ulist);
		mockMvc.perform(get("/api/UnregisterCarReport").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3)));
	}

	private String convertJsonString(Object object) {
		try {
			ObjectMapper op = new ObjectMapper();
			op.findAndRegisterModules();
			return op.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.getStackTrace();
		}
		return null;
	}
}
