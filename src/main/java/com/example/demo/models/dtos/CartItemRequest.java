package com.example.demo.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemRequest {
    @NotNull
    private Long shoeId;

    @NotNull
    @Min(1)
    private Integer amount;
}