package com.example.demo.models.dtos;

import jakarta.validation.constraints.*;

// Snake-case names keep the frontend registration contract unchanged.
public record RegistrationRequest(
    @NotBlank @Size(max=100) String first_name,
    @NotBlank @Size(max=100) String last_name,
    @NotBlank @Email String email,
    @NotBlank @Size(max=20) String postcode,
    @NotBlank @Size(max=20) String house_number,
    @NotNull @Size(min=8, max=128) String password,
    @NotNull boolean admin
) {}
