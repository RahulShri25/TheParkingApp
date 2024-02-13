package com.car.controller;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.car.config.Constant;
import com.car.entity.ParkingDetail;
import com.car.entity.ParkingObservDetail;
import com.car.entity.ParkingObservdto;
import com.car.service.ParkingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

		Optional<ParkingDetail> val = Optional.of(inputdata);

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

		ParkingDetail val = new ParkingDetail();
		val.setLicenceNumber(inputdata.getLicenceNumber());
		val.setStreetName("Jakarta");
		val.setCurrentStatus(Constant.Car_UnRegistered);
		inputdata.setArrivalTime(LocalDateTime.now().withNano(0).minusMinutes(5));
		inputdata.setDepartureTime(LocalDateTime.now().withNano(0));
		Optional<ParkingDetail> op = Optional.of(val);

		Mockito.when(parkingService.registerCar(ArgumentMatchers.any())).thenReturn(inputdata);
		mockMvc.perform(post("/api/unregistercar").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(convertJsonString(inputdata)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.parkingAmount", Matchers.equalTo(BigDecimal.ZERO.intValue())));

	}

	@Test
	void addParkingObservationDetailTest() throws Exception {

		ParkingObservdto parkObvDto = new ParkingObservdto();

		ParkingObservDetail observ1 = new ParkingObservDetail();
		observ1.setLicenceNumber("abc110");
		observ1.setStreetName("Jakarta");
		observ1.setRecordingDate(LocalDateTime.now().withNano(0));

		ParkingObservDetail observ2 = new ParkingObservDetail();
		observ1.setLicenceNumber("abc111");
		observ1.setStreetName("Java");
		observ1.setRecordingDate(LocalDateTime.now().withNano(0));

		parkObvDto.setParkingObservDetail(List.of(observ1, observ2));

		Mockito.when(parkingService.addParkingObservDetail(ArgumentMatchers.any()))
				.thenReturn(List.of(observ1, observ2));
		mockMvc.perform(post("/api/addParkingObservdetail").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(convertJsonString(parkObvDto)).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$[0].licenceNumber", Matchers.equalTo("abc109")))
				.andExpect(jsonPath("$[1].licenceNumber", Matchers.equalTo("abc109")))
				.andExpect(jsonPath("$", Matchers.hasSize(3)));
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
