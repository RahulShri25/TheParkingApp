package com.car.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.PriceDetail;

@Repository
public interface ParkingCostRepository extends JpaRepository<PriceDetail, Long> {
}
