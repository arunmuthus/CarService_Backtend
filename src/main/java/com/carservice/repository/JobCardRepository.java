package com.carservice.repository;

import com.carservice.entity.JobCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {
    List<JobCard> findByStatus(String status);

    @Query("SELECT j FROM JobCard j WHERE j.vehicle.id = :vehicleId AND j.status IN ('CREATED', 'IN_PROGRESS')")
    Optional<JobCard> findActiveJobByVehicleId(@Param("vehicleId") Long vehicleId);
}
