package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Integer> {
}
