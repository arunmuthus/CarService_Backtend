package com.carservice.controller;

import com.carservice.entity.InventoryItem;
import com.carservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:5173")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<InventoryItem> getAllItems() {
        return inventoryRepository.findAll();
    }

    @PostMapping
    public InventoryItem addItem(@RequestBody InventoryItem item) {
        return inventoryRepository.save(item);
    }

    @PutMapping("/{id}")
    public InventoryItem updateItem(@PathVariable Long id, @RequestBody InventoryItem itemDetails) {
        return inventoryRepository.findById(id).map(item -> {
            item.setName(itemDetails.getName());
            item.setSku(itemDetails.getSku());
            item.setQuantity(itemDetails.getQuantity());
            item.setPrice(itemDetails.getPrice());
            return inventoryRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
    }
}
