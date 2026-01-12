package com.carservice.repository;

import com.carservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCustomerId(Long customerId);

    List<Vehicle> findByLicensePlate(String licensePlate);
}
