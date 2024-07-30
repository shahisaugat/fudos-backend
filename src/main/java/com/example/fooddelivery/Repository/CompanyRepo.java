package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Integer> {
}
