
package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController {

    @GetMapping
    public List<Map<String, Object>> getShoes() {
        return List.of(
                Map.of("id", 1, "name", "Nike Air Max", "price", 129.99),
                Map.of("id", 2, "name", "Adidas Samba", "price", 99.99),
                Map.of("id", 3, "name", "New Balance 550", "price", 119.99)
        );
    }
}

