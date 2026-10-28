package com.example.demo.controllers;

import com.example.demo.models.Shoe;
import com.example.demo.models.dtos.ShoeRequest;
import com.example.demo.models.dtos.ShoeResponse;
import com.example.demo.services.ShoeService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoeControllerTest {

    @Test
    void shouldReturnAllShoes() {
        ShoeService shoeService = mock(ShoeService.class);
        ShoeController controller = new ShoeController(shoeService);
        ShoeResponse shoe = new ShoeResponse();
        shoe.setName("Nike Air Max");
        when(shoeService.getAllShoes()).thenReturn(List.of(shoe));

        var response = controller.getAllShoes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Nike Air Max", response.getBody().get(0).getName());
        verify(shoeService).getAllShoes();
    }

    @Test
    void shouldCreateShoe() {
        ShoeService shoeService = mock(ShoeService.class);
        ShoeController controller = new ShoeController(shoeService);
        ShoeRequest request = new ShoeRequest();
        ShoeResponse shoe = new ShoeResponse();
        shoe.setId(1L);
        shoe.setName("Nike Air Max");
        when(shoeService.createShoe(request)).thenReturn(shoe);

        var response = controller.createShoe(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(shoe, response.getBody());
        verify(shoeService).createShoe(request);
    }

    @Test
    void shouldGetShoeById() {
        ShoeService shoeService = mock(ShoeService.class);
        ShoeController controller = new ShoeController(shoeService);
        ShoeResponse shoe = new ShoeResponse();
        shoe.setId(1L);
        when(shoeService.getShoeById(1L)).thenReturn(shoe);

        var response = controller.getShoeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe, response.getBody());
        verify(shoeService).getShoeById(1L);
    }

    @Test
    void shouldUpdateShoe() {
        ShoeService shoeService = mock(ShoeService.class);
        ShoeController controller = new ShoeController(shoeService);
        ShoeRequest request = new ShoeRequest();
        ShoeResponse shoe = new ShoeResponse();
        shoe.setId(1L);
        when(shoeService.updateShoe(1L, request)).thenReturn(shoe);

        var response = controller.updateShoe(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoe, response.getBody());
        verify(shoeService).updateShoe(1L, request);
    }

    @Test
    void shouldDeleteShoe() {
        ShoeService shoeService = mock(ShoeService.class);
        ShoeController controller = new ShoeController(shoeService);

        var response = controller.deleteShoe(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(shoeService).deleteShoe(1L);
    }
}