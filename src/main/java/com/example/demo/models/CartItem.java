package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Entity
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "cartitems") 
public class CartItem {
    
    @Id 
    @GeneratedValue
    private long id;

    private int amount;

    @ManyToOne 
    private Shoe shoe;

    @ManyToOne
    private Cart cart;
}
