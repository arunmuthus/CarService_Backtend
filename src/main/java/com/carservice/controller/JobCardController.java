package com.carservice.controller;

import com.carservice.dto.JobCardRequest;
import com.carservice.dto.AddPartRequest;
import com.carservice.entity.JobCard;
import com.carservice.service.JobCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-cards")
public class JobCardController {

    @Autowired
    private JobCardService jobCardService;

    @GetMapping
    public List<JobCard> getAllJobCards() {
        return jobCardService.getAllJobCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCard> getJobCard(@PathVariable Long id) {
        return ResponseEntity.ok(jobCardService.getJobCard(id));
    }

    @PostMapping
    public ResponseEntity<JobCard> createJobCard(@RequestBody JobCardRequest request) {
        return ResponseEntity.ok(jobCardService.createJobCard(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<JobCard> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(jobCardService.updateStatus(id, status));
    }

    @PostMapping("/{id}/parts")
    public ResponseEntity<JobCard> addSparePart(@PathVariable Long id, @RequestBody AddPartRequest request) {
        return ResponseEntity.ok(jobCardService.addSparePart(id, request));
    }
}
