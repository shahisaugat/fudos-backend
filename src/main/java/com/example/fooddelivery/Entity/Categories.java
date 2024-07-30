package com.example.fooddelivery.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table

public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="Category Name")
    private String categoryName;

    @Column(name="description")
    private  String description;
}
