package com.example.fooddelivery.Repository;

import com.example.fooddelivery.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long>{
}
