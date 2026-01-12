package com.carservice.controller;

import com.carservice.entity.Vehicle;
import com.carservice.repository.VehicleRepository;
import com.carservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/search")
    public ResponseEntity<Vehicle> getVehicleByPlate(@RequestParam String plate) {
        List<Vehicle> vehicles = vehicleRepository.findByLicensePlate(plate);
        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicles.get(0));
    }

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        if (vehicle.getCustomer() != null && vehicle.getCustomer().getId() == null) {
            customerRepository.save(vehicle.getCustomer());
        }
        return vehicleRepository.save(vehicle);
    }
}
