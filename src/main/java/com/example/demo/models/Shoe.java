package com.example.demo.models;

import java.util.List;

import com.example.demo.models.enums.Brand;
import com.example.demo.models.enums.Color;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shoes")
public class Shoe {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private float price;

    private int stock;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Color> colors;
}