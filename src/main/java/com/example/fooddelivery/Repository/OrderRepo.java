package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Integer> {
}
