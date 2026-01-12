package com.carservice;

import com.carservice.entity.Customer;
import com.carservice.entity.User;
import com.carservice.entity.Vehicle;
import com.carservice.repository.CustomerRepository;
import com.carservice.repository.UserRepository;
import com.carservice.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User("advisor", "password", "ADVISOR"));
            userRepository.save(new User("tech", "password", "TECHNICIAN"));
            userRepository.save(new User("admin", "password", "ADMIN"));
            System.out.println("Seeded default users");
        }

        if (customerRepository.count() == 0) {
            Customer c1 = new Customer("Alice Johnson", "alice@example.com", "555-0101", "123 Maple St");
            Customer c2 = new Customer("Bob Smith", "bob@example.com", "555-0102", "456 Oak Ave");

            customerRepository.save(c1);
            customerRepository.save(c2);

            Vehicle v1 = new Vehicle("Toyota", "Camry", "2020", "CAM-01");
            v1.setCustomer(c1);
            Vehicle v2 = new Vehicle("Honda", "Civic", "2019", "CIV-02");
            v2.setCustomer(c1);
            Vehicle v3 = new Vehicle("Ford", "F-150", "2021", "FRD-03");
            v3.setCustomer(c2);

            vehicleRepository.save(v1);
            vehicleRepository.save(v2);
            vehicleRepository.save(v3);

            System.out.println("Seeded Customers and Vehicles");
        }
    }
}
