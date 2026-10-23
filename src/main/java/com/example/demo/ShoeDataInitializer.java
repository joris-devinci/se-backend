package com.example.demo;

import com.example.demo.models.Shoe;
import com.example.demo.repositories.ShoeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ShoeDataInitializer {

    @Bean
    CommandLineRunner initializeShoes(ShoeRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                Shoe shoe1 = new Shoe();
                shoe1.setName("Nike Air Max");
                shoe1.setPrice(new BigDecimal("129.99"));
                shoe1.setColor("Black");
                shoe1.setBrand("Nike");
                shoe1.setCategory("Sneakers");

                Shoe shoe2 = new Shoe();
                shoe2.setName("Adidas Samba");
                shoe2.setPrice(new BigDecimal("99.99"));
                shoe2.setColor("White");
                shoe2.setBrand("Adidas");
                shoe2.setCategory("Casual");

                Shoe shoe3 = new Shoe();
                shoe3.setName("New Balance 550");
                shoe3.setPrice(new BigDecimal("119.99"));
                shoe3.setColor("Grey");
                shoe3.setBrand("New Balance");
                shoe3.setCategory("Lifestyle");

                repository.save(shoe1);
                repository.save(shoe2);
                repository.save(shoe3);
            }
        };
    }
}