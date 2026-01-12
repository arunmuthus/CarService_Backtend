package com.carservice.repository;

import com.carservice.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SparePartRepository extends JpaRepository<SparePart, Long> {
}
