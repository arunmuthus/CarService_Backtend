package com.carservice.service;

import com.carservice.dto.JobCardRequest;
import com.carservice.dto.AddPartRequest;
import com.carservice.entity.JobCard;
import com.carservice.entity.SparePart;
import com.carservice.entity.Vehicle;
import com.carservice.entity.InventoryItem;
import com.carservice.exception.ResourceNotFoundException;
import com.carservice.repository.JobCardRepository;
import com.carservice.repository.VehicleRepository;
import com.carservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobCardService {

    @Autowired
    private JobCardRepository jobCardRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<JobCard> getAllJobCards() {
        return jobCardRepository.findAll();
    }

    public JobCard getJobCard(Long id) {
        return jobCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobCard not found with id: " + id));
    }

    @Transactional
    public JobCard createJobCard(JobCardRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vehicle not found with id: " + request.getVehicleId()));

        if (jobCardRepository.findActiveJobByVehicleId(vehicle.getId()).isPresent()) {
            throw new RuntimeException("Vehicle already has an active Job Card. Please complete it first.");
        }

        JobCard jobCard = new JobCard();
        jobCard.setDescription(request.getDescription());
        jobCard.setVehicle(vehicle);

        return jobCardRepository.save(jobCard);
    }

    @Transactional
    public JobCard updateStatus(Long id, String status) {
        JobCard jobCard = getJobCard(id);

        jobCard.setStatus(status);
        LocalDateTime now = LocalDateTime.now();

        if ("IN_PROGRESS".equals(status)) {
            if (jobCard.getWorkStartTime() == null) {
                jobCard.setWorkStartTime(now);
            }
        } else if ("COMPLETED".equals(status)) {
            jobCard.setWorkEndTime(now);
            calculateTotalCost(jobCard);
        } else if ("DELIVERED".equals(status)) {
            jobCard.setVehicleOutTime(now);
        }

        return jobCardRepository.save(jobCard);
    }

    @Transactional
    public JobCard addSparePart(Long id, AddPartRequest request) {
        JobCard jobCard = getJobCard(id);
        SparePart part = new SparePart();

        if (request.getInventoryItemId() != null) {
            // Inventory Logic
            InventoryItem item = inventoryRepository.findById(request.getInventoryItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory Item not found"));

            Integer qty = item.getQuantity() != null ? item.getQuantity() : 0;
            if (qty <= 0) {
                throw new com.carservice.exception.BadRequestException("Item is out of stock!");
            }

            item.setQuantity(qty - 1);
            inventoryRepository.save(item);

            part.setName(item.getName());
            Double price = item.getPrice() != null ? item.getPrice() : 0.0;
            part.setCost(price);
        } else {
            // Manual Logic
            part.setName(request.getName());
            part.setCost(request.getCost() != null ? request.getCost() : 0.0);
        }

        jobCard.addSparePart(part);
        calculateTotalCost(jobCard);
        return jobCardRepository.save(jobCard);
    }

    private void calculateTotalCost(JobCard jobCard) {
        double partsCost = jobCard.getSpareParts().stream()
                .mapToDouble(p -> p.getCost() != null ? p.getCost() : 0.0)
                .sum();
        double labor = 500.0 + (partsCost * 0.10);

        jobCard.setLaborCost(Math.round(labor * 100.0) / 100.0);
        jobCard.setTotalCost(Math.round((partsCost + labor) * 100.0) / 100.0);
    }
}
