package com.carservice.controller;

import com.carservice.dto.CustomerLoginRequest;
import com.carservice.entity.User;
import com.carservice.entity.Vehicle;
import com.carservice.repository.UserRepository;
import com.carservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        return userRepository.findByUsername(loginUser.getUsername())
                .filter(user -> user.getPassword().equals(loginUser.getPassword()))
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/customer-login")
    public ResponseEntity<?> customerLogin(@RequestBody CustomerLoginRequest request) {
        List<Vehicle> vehicles = vehicleRepository.findByLicensePlate(request.getLicensePlate());

        for (Vehicle v : vehicles) {
            String vPin = v.getPin() != null ? v.getPin() : "1234";
            if (vPin.equals(request.getPin())) {
                Map<String, Object> response = new HashMap<>();
                response.put("role", "CUSTOMER");
                response.put("vehicleId", v.getId());
                response.put("plate", v.getLicensePlate());
                response.put("customerName", v.getCustomer() != null ? v.getCustomer().getName() : "Unknown");
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401).body("Invalid Vehicle Number or PIN");
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/debug/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
