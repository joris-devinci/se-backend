package com.example.demo.controllers;

import com.example.demo.models.dtos.ShoeRequest;
import com.example.demo.models.dtos.ShoeResponse;
import com.example.demo.services.ShoeService;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController {

    private final ShoeService shoeService;

    public ShoeController(ShoeService shoeService) {
        this.shoeService = shoeService;
    }

    @GetMapping
    public ResponseEntity<List<ShoeResponse>> getAllShoes() {
        return ResponseEntity.status(HttpStatus.OK).body(shoeService.getAllShoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoeResponse> getShoeById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(shoeService.getShoeById(id));
    }

    @PostMapping
    public ResponseEntity<ShoeResponse> createShoe(@Valid @RequestBody ShoeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoeService.createShoe(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoeResponse> updateShoe(@PathVariable long id, @Valid @RequestBody ShoeRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(shoeService.updateShoe(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoe(@PathVariable long id) {
        shoeService.deleteShoe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}