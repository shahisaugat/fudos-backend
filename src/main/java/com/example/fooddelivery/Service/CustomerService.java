package com.example.fooddelivery.Service;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Pojo.CustomerPojo;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void addCustomer(CustomerPojo customerPojo);

    void deleteById(Integer id);

    List<Customer> getAll();

    Optional<Customer> findById(Integer id);
    void updateData(Integer id, CustomerPojo customerPojo);
    boolean existsById(Integer id);
    Long customerCount();
}
