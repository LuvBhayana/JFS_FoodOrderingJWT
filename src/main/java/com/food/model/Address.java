package com.food.model;


import jakarta.persistence.*;

@Entity

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
