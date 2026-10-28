package com.example.demo.controllers;

import java.util.*;
import com.example.demo.models.User;
import com.example.demo.models.Cart;
import com.example.demo.models.dtos.*;
import com.example.demo.repositories.*;
import com.example.demo.services.PasswordService;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class AuthController {
    private final UserRepository users;
    private final CartRepository carts;
    private final PasswordService passwords;
    public AuthController(UserRepository users, CartRepository carts, PasswordService passwords) {
        this.users=users; this.carts=carts; this.passwords=passwords;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest data) {
        String email=data.email().trim().toLowerCase(Locale.ROOT);
        if (users.findByEmailIgnoreCase(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An account with this email already exists.");
        }
        User user=new User();
        user.setFirstName(data.first_name().trim()); user.setLastName(data.last_name().trim());
        user.setName(user.getFirstName()+" "+user.getLastName());
        user.setEmail(email); user.setPostcode(data.postcode().trim());
        user.setHouseNumber(data.house_number().trim()); user.setPassword(passwords.hash(data.password()));
        user.setAdmin(data.admin());
        users.saveAndFlush(user);
        // Existing cart endpoints expect every user to have a cart.
        Cart cart=new Cart(); cart.setUser(user); cart.setCartItems(new ArrayList<>());
        carts.save(cart); user.setCart(cart);
        return ResponseEntity.status(201).body(Map.of("id",user.getId(),"message","Account created successfully."));
    }

    @PostMapping("/login")
    public Map<String,String> login(@Valid @RequestBody LoginRequest data, HttpServletRequest request) {
        User user=users.findByEmailIgnoreCase(data.email().trim()).orElse(null);
        if (user==null || !passwords.matches(data.password(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password.");
        }
        // Replace the session on login so an old cookie cannot be reused.
        HttpSession old=request.getSession(false);
        if (old!=null) old.invalidate();
        HttpSession session=request.getSession(true);
        session.setAttribute("userId",user.getId());
        return Map.of("message","Logged in successfully.");
    }

    @GetMapping("/me")
    public Map<String,Object> me(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if (session==null || session.getAttribute("userId")==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please log in to continue.");
        }
        User user=users.findById((Long)session.getAttribute("userId")).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please log in to continue."));
        // Explicit response fields prevent passwords and entity relationships leaking.
        return Map.of("id",user.getId(),"first_name",user.getFirstName(),"last_name",user.getLastName(),
            "email",user.getEmail(),"postcode",user.getPostcode(),"house_number",user.getHouseNumber());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        if (session!=null) session.invalidate();
        ResponseCookie cookie=ResponseCookie.from("session_id","").path("/").httpOnly(true)
            .sameSite("Lax").secure(request.isSecure()).maxAge(0).build();
        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE,cookie.toString()).build();
    }
}
