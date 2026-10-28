
package com.example.demo;

import com.example.demo.models.Shoe;
import com.example.demo.models.enums.Brand;
import com.example.demo.models.enums.Color;
import com.example.demo.repositories.ShoeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ShoeDataInitializer {

    @Bean
    CommandLineRunner initializeShoes(ShoeRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                Shoe shoe1 = new Shoe();
                shoe1.setName("Nike Air Max");
                shoe1.setPrice(129.99f);
                shoe1.setStock(10);
                shoe1.setImageUrl("nothing");
                shoe1.setBrand(Brand.NIKE);
                shoe1.setColors(List.of(Color.BLACK));

                Shoe shoe2 = new Shoe();
                shoe2.setName("Adidas Samba");
                shoe2.setPrice(99.99f);
                shoe2.setStock(15);
                shoe2.setImageUrl("nothing");
                shoe2.setBrand(Brand.ADIDAS);
                shoe2.setColors(List.of(Color.WHITE));

                Shoe shoe3 = new Shoe();
                shoe3.setName("Puma Suede");
                shoe3.setPrice(89.99f);
                shoe3.setStock(8);
                shoe3.setImageUrl("nothing");
                shoe3.setBrand(Brand.PUMA);
                shoe3.setColors(List.of(Color.GREEN));

                repository.saveAll(List.of(shoe1, shoe2, shoe3));
            }
        };
    }
}
