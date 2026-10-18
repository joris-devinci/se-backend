package com.example.demo.repositories;

import com.example.demo.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser_Id(Long userId);
}