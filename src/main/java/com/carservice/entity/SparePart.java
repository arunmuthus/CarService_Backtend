package com.carservice.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "spare_parts")
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_card_id")
    @JsonIgnore
    private JobCard jobCard;

    public SparePart() {
    }

    public SparePart(String name, Double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public JobCard getJobCard() {
        return jobCard;
    }

    public void setJobCard(JobCard jobCard) {
        this.jobCard = jobCard;
    }
}
