package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Categories,Integer> {
}
