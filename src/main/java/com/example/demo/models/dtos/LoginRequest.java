package com.example.demo.models.dtos;
import jakarta.validation.constraints.*;
public record LoginRequest(@NotBlank @Email String email,
                           @NotNull @Size(min=1, max=128) String password) {}
