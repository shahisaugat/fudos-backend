package com.example.fooddelivery.Service.Impl;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Pojo.CustomerPojo;
import com.example.fooddelivery.Repository.CustomerRepo;
import com.example.fooddelivery.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    @Override
    public void addCustomer(CustomerPojo customerPojo) {
        Customer customer = new Customer();
        customer.setId(Long.valueOf(customerPojo.getId()));
//        customer.setName(customerPojo.getName());
//        customer.setLocation(customerPojo.getLocation());
////        customer.setTelNo(customerPojo.getTelNo());
//        customer.setContactEmail(customerPojo.getContactEmail());
        customerRepo.save(customer);
    }

    @Override
    public void deleteById(Integer id) {
        this.customerRepo.deleteById(id);


    }

    @Override
    public List<Customer> getAll() {
        return this.customerRepo.findAll();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return this.customerRepo.findById(id);
    }

    public void updateData(Integer id, CustomerPojo customerPojo) {
        Optional<Customer> custoomerOptional = customerRepo.findById(id);
        if (custoomerOptional.isPresent()) {
            Customer existingCustomer = custoomerOptional.get();

            updateCustomerProperties(existingCustomer, customerPojo);
            customerRepo.save(existingCustomer); // Save the updated student
        } else {
            // Handle the case where the student with the given ID does not exist
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
    }



    private void updateCustomerProperties(Customer customer, CustomerPojo customerPojo) {
        customer.setId(Long.valueOf(customerPojo.getId()));
//        customer.setName(customerPojo.getName());
//        customer.setLocation(customerPojo.getLocation());
////        customer.setTelNo(customerPojo.getTelNo());
//        customer.setContactEmail(customerPojo.getContactEmail());
        customerRepo.save(customer);




    }

    @Override
    public boolean existsById(Integer id) {
        return this.customerRepo.existsById(id);
    }

    @Override
    public Long customerCount() {
        return this.customerRepo.count();
    }
}
