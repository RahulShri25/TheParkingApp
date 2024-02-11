package com.car.respository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.car.entity.ParkingDetail;

public interface ParkingRepository extends JpaRepository<ParkingDetail, Long>{

	Optional<ParkingDetail>  findByLicenceNumber(String licenceNumber);
	
	@Query("SELECT pd FROM ParkingDetail pd WHERE pd.licenceNumber = ?1 AND currentStatus = ?2")
	Optional<ParkingDetail> findByLicenceNumberAndCurrentStatus(String licenceNumber, String parkingStatus);
	
	List<ParkingDetail> findByArrivalTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
