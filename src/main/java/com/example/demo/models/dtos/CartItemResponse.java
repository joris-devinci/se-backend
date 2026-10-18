package com.example.demo.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemResponse {
    private long id;
    private int amount;
    private long shoeId;
    private String shoeName;
    private float shoePrice;
}