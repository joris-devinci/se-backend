package com.example.demo.models.dtos;

import java.util.List;

import com.example.demo.models.enums.Brand;
import com.example.demo.models.enums.Color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoeRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @PositiveOrZero
    private Float price;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    private String imageUrl;

    @NotNull
    private Brand brand;

    @NotEmpty
    private List<Color> colors;
}
