package com.example.demo.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.models.dtos.UserResponse;
import com.example.demo.repositories.UserRepository;

@Service 
public class UserService {
    
    private UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = users.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
        return response;
    }

    public UserResponse getById(long id) {
        User user = userRepository.getReferenceById(id);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        return response;
    }
}
