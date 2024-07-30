package com.example.fooddelivery;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Pojo.CustomerPojo;
import com.example.fooddelivery.Repository.CustomerRepo;
import com.example.fooddelivery.Service.Impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerTest {

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomer() {
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.setId(Integer.valueOf("1"));

        customerService.addCustomer(customerPojo);

        verify(customerRepo, times(1)).save(argThat(customer ->
                        customer.getId().equals(Long.valueOf(customerPojo.getId()))
        ));
    }

    @Test
    public void testDeleteById() {
        Integer customerId = 1;

        customerService.deleteById(customerId);

        verify(customerRepo, times(1)).deleteById(customerId);
    }

    @Test
    public void testGetAll() {
        Customer customer = new Customer();
        when(customerRepo.findAll()).thenReturn(List.of(customer));

        List<Customer> customers = customerService.getAll();

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        assertEquals(customer, customers.get(0));
    }

    @Test
    public void testFindById() {
        Integer customerId = 1;
        Customer customer = new Customer();
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.findById(customerId);

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer, foundCustomer.get());
    }



    @Test
    public void testUpdateDataCustomerNotFound() {
        Integer customerId = 1;
        CustomerPojo customerPojo = new CustomerPojo();
        customerPojo.setId(Integer.valueOf("1"));

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                customerService.updateData(customerId, customerPojo)
        );

        assertEquals("Student with ID " + customerId + " not found", thrown.getMessage());
    }

    @Test
    public void testExistsById() {
        Integer customerId = 1;
        when(customerRepo.existsById(customerId)).thenReturn(true);

        boolean exists = customerService.existsById(customerId);

        assertTrue(exists);
    }

    @Test
    public void testCustomerCount() {
        Long count = 10L;
        when(customerRepo.count()).thenReturn(count);

        Long customerCount = customerService.customerCount();

        assertEquals(count, customerCount);
    }
}
