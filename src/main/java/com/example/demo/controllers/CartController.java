package com.example.demo.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.CartItemRequest;
import com.example.demo.models.dtos.CartResponse;
import com.example.demo.services.CartService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/cart")
public class CartController {
	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<CartResponse> getCart(@PathVariable long userId) {
		CartResponse response = cartService.getCartByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/{userId}/items")
	public ResponseEntity<CartResponse> addCartItem(@PathVariable long userId,
			@Valid @RequestBody CartItemRequest request) {
		CartResponse response = cartService.addCartItem(userId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{userId}/items/{cartItemId}/increase")
	public ResponseEntity<CartResponse> increaseCartItem(@PathVariable long userId, @PathVariable long cartItemId) {
		CartResponse response = cartService.increaseCartItem(userId, cartItemId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{userId}/items/{cartItemId}/decrease")
	public ResponseEntity<CartResponse> decreaseCartItem(@PathVariable long userId, @PathVariable long cartItemId) {
		CartResponse response = cartService.decreaseCartItem(userId, cartItemId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{userId}/items/{cartItemId}")
	public ResponseEntity<CartResponse> removeCartItem(@PathVariable long userId, @PathVariable long cartItemId) {
		CartResponse response = cartService.removeCartItem(userId, cartItemId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
}
