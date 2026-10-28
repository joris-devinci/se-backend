package com.example.demo.services;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Shoe;
import com.example.demo.models.dtos.ShoeRequest;
import com.example.demo.models.dtos.ShoeResponse;
import com.example.demo.repositories.ShoeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoeService {

    private final ShoeRepository shoeRepository;
    private final ModelMapper modelMapper;

    public ShoeService(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ShoeResponse> getAllShoes() {
        return shoeRepository.findAll().stream()
                .map(shoe -> modelMapper.map(shoe, ShoeResponse.class))
                .toList();
    }

    public ShoeResponse getShoeById(long id) {
        Shoe shoe = shoeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shoe not found."));
        return modelMapper.map(shoe, ShoeResponse.class);
    }

    public ShoeResponse createShoe(ShoeRequest request) {
        Shoe shoe = modelMapper.map(request, Shoe.class);
        Shoe savedShoe = shoeRepository.save(shoe);
        return modelMapper.map(savedShoe, ShoeResponse.class);
    }

    public ShoeResponse updateShoe(long id, ShoeRequest request) {
        Shoe shoe = shoeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shoe not found."));
        modelMapper.map(request, shoe);
        Shoe savedShoe = shoeRepository.save(shoe);
        return modelMapper.map(savedShoe, ShoeResponse.class);
    }

    public void deleteShoe(long id) {
        Shoe shoe = shoeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shoe not found."));
        shoeRepository.delete(shoe);
    }
}