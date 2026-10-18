package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Shoe;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {
}