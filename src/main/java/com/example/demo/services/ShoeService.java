package com.example.demo.services;

import com.example.demo.models.Shoe;
import com.example.demo.repositories.ShoeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoeService {

    private final ShoeRepository shoeRepository;

    public ShoeService(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    public List<Shoe> getAllShoes() {
        return shoeRepository.findAll();
    }
}