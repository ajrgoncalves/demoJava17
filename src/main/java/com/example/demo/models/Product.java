package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    Category category;


}
