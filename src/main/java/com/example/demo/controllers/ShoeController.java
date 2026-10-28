package com.example.demo.controllers;

import com.example.demo.models.Shoe;
import com.example.demo.services.ShoeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Shoe;

import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController {

    private final ShoeService shoeService;

    public ShoeController(ShoeService shoeService) {
        this.shoeService = shoeService;
    }

    @GetMapping
    public List<Shoe> getShoes() {
        return shoeService.getAllShoes();
    }
}