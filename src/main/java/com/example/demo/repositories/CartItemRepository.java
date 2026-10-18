package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart_IdAndShoe_Id(Long cartId, Long shoeId);

    Optional<CartItem> findByIdAndCart_Id(Long id, Long cartId);

    void deleteByIdAndCart_Id(Long id, Long cartId);
}