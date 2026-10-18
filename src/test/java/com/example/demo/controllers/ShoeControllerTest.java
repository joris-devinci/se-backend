
package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoeControllerTest {

    @Test
    void shouldReturnThreeShoes() {
        ShoeController controller = new ShoeController();

        List<Map<String, Object>> shoes = controller.getShoes();

        assertEquals(3, shoes.size());
        assertEquals("Nike Air Max", shoes.get(0).get("name"));
    }
}

