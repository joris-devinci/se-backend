package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.enums.Brand;
import com.example.demo.models.enums.Color;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoeResponse {
    private long id;
    private String name;
    private float price;
    private int stock;
    private String imageUrl;
    private Brand brand;
    private List<Color> colors;
}
