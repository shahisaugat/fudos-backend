package com.example.fooddelivery.Repository;


import com.example.fooddelivery.Entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ItemsRepo extends JpaRepository<Items,Integer> {
}
