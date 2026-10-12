package com.example.demo.controllers;

import com.example.demo.models.dtos.UserRequest;
import com.example.demo.models.dtos.UserResponse;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable long id) {
        UserResponse response = userService.getById(id);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> add(@RequestBody UserRequest user) {
        UserResponse response = new UserResponse();
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable long id, @RequestBody UserRequest user) {
        UserResponse response = new UserResponse();
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return ResponseEntity.status(204).build();
    }
}