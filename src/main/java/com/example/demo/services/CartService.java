package com.example.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exceptions.ConflictException;
import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;
import com.example.demo.models.Shoe;
import com.example.demo.models.dtos.CartItemRequest;
import com.example.demo.models.dtos.CartResponse;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ShoeRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoeRepository shoeRepository;
    private final ModelMapper modelMapper;

    public CartService(
        CartRepository cartRepository, 
        CartItemRepository cartItemRepository,
        ShoeRepository shoeRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.shoeRepository = shoeRepository;
        this.modelMapper = new ModelMapper();
    }

    // ACTION INSIDE THE SHOE BROWSER

    @Transactional
    public CartResponse addCartItem(long userId, CartItemRequest request) {
        Cart cart = cartRepository.findByUser_Id(userId);
        Shoe shoe = shoeRepository.getReferenceById(request.getShoeId());
        
        // If cart already has this shoe in its cart just add values to it
        CartItem cartItem = cartItemRepository.findByCart_IdAndShoe_Id(cart.getId(), shoe.getId())
            .orElseGet(() -> {
                CartItem newItem = new CartItem(0L, 0, shoe, cart);
                cart.getCartItems().add(newItem);
                return newItem;
            });
        
        cartItem.setAmount(cartItem.getAmount() + request.getAmount());
        cartItemRepository.save(cartItem);

        return modelMapper.map(cart, CartResponse.class);
    }

    // ACTIONS INSIDE THE CART

    @Transactional
    public CartResponse getCartByUserId(long userId) {
        Cart cart = cartRepository.findByUser_Id(userId);
        CartResponse response = modelMapper.map(cart, CartResponse.class);
        return response;
    }

    @Transactional
    public CartResponse increaseCartItem(long userId, long cartItemId) {
        
        CartItem cartItem = cartItemRepository.getReferenceById(cartItemId);
        cartItem.setAmount(cartItem.getAmount() + 1);
        cartItemRepository.save(cartItem);
        
        Cart cart = cartRepository.findByUser_Id(userId);
        return modelMapper.map(cart, CartResponse.class);
    }

    @Transactional
    public CartResponse decreaseCartItem(long userId, long cartItemId) {
        
        CartItem cartItem = cartItemRepository.getReferenceById(cartItemId);
        if (cartItem.getAmount() <= 1) {
            throw new ConflictException(
                "Can't decrease this item because its already on one\n" +
                "Maybe try using the remove button"
            );
        }
        cartItem.setAmount(cartItem.getAmount() - 1);
        cartItemRepository.save(cartItem);
        
        Cart cart = cartRepository.findByUser_Id(userId);
        return modelMapper.map(cart, CartResponse.class);
    }

    @Transactional
    public CartResponse removeCartItem(long userId, long cartItemId) {
        Cart cart = cartRepository.findByUser_Id(userId);

        cartItemRepository.deleteByIdAndCart_Id(cartItemId, cart.getId());
    
        return modelMapper.map(cart, CartResponse.class);
    }
}
