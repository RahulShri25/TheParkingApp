package com.car.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.ParkingObservDetail;

@Repository
public interface ParkingObservationDetail extends JpaRepository<ParkingObservDetail,Long>{
	List<ParkingObservDetail> findByRecordingDateBetween(LocalDateTime previousRecordingDate, LocalDateTime recordingDate);
}
