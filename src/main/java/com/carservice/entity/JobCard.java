package com.carservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "job_cards")
public class JobCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String description;

    private String status; // CREATED, IN_PROGRESS, COMPLETED, DELIVERED

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated; // Same as vehicleInTime usually

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vehicleInTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime workEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vehicleOutTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "jobCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SparePart> spareParts = new ArrayList<>();

    // Logic fields
    private Double laborCost = 0.0;
    private Double totalCost = 0.0;

    public JobCard() {
        this.dateCreated = LocalDateTime.now();
        this.vehicleInTime = LocalDateTime.now(); // Default
        this.status = "CREATED";
    }

    public void addSparePart(SparePart part) {
        spareParts.add(part);
        part.setJobCard(this);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getVehicleInTime() {
        return vehicleInTime;
    }

    public void setVehicleInTime(LocalDateTime vehicleInTime) {
        this.vehicleInTime = vehicleInTime;
    }

    public LocalDateTime getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(LocalDateTime workStartTime) {
        this.workStartTime = workStartTime;
    }

    public LocalDateTime getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(LocalDateTime workEndTime) {
        this.workEndTime = workEndTime;
    }

    public LocalDateTime getVehicleOutTime() {
        return vehicleOutTime;
    }

    public void setVehicleOutTime(LocalDateTime vehicleOutTime) {
        this.vehicleOutTime = vehicleOutTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}
