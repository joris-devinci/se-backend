package com.example.demo.models.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartResponse {
    private long id;
    private long userId;
    private List<CartItemResponse> items;
}