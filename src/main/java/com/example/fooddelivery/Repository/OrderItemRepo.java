package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Integer>{
}
