package com.example.demo.controllers;

import com.example.demo.models.Shoe;
import com.example.demo.services.ShoeService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoeControllerTest {

    @Test
    void shouldReturnAllShoes() {

        // Create a mock service
        ShoeService shoeService = mock(ShoeService.class);

        // Create controller with the mock service
        ShoeController controller = new ShoeController(shoeService);

        // Define what the mock service should return
        Shoe shoe = new Shoe();
        shoe.setName("Nike Air Max");

        when(shoeService.getAllShoes()).thenReturn(List.of(shoe));

        // Call the controller
        List<Shoe> shoes = controller.getShoes();

        // Verify the result
        assertEquals(1, shoes.size());
        assertEquals("Nike Air Max", shoes.get(0).getName());

        // Verify that the service was called
        verify(shoeService).getAllShoes();
    }
}