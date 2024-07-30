package com.example.fooddelivery.Controller;

import com.example.fooddelivery.Entity.Customer;
import com.example.fooddelivery.Entity.OrderItem;
import com.example.fooddelivery.Entity.Orders;
import com.example.fooddelivery.Entity.Product;
import com.example.fooddelivery.Pojo.OrderPojo;
import com.example.fooddelivery.Repository.CustomerRepo;
import com.example.fooddelivery.Repository.ProductRepo;
import com.example.fooddelivery.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final CustomerRepo customerRepository;
    private final OrderService orderService;
    private final ProductRepo productRepository;



    @PostMapping
    public void placeOrder(@RequestBody OrderPojo orderDTO) {
        Orders order = new Orders();
        order.setName(orderDTO.getName());
        order.setEmail(orderDTO.getEmail());
        order.setAddress(orderDTO.getAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setSubtotal(orderDTO.getSubtotal());
        order.setShipping(orderDTO.getShipping());
        order.setTax(orderDTO.getTax());
        order.setTotal(orderDTO.getTotal());



//         Set customer
        Customer customer = customerRepository.findById(orderDTO.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + orderDTO.getCustomer().getId()));
        order.setCustomer(customer);

        List<OrderItem> orderItems = orderDTO.getCartItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + itemDTO.getProductId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        order.setCartItems(orderItems);
        orderService.placeOrder(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Orders>> getAllOrders() {
        Iterable<Orders> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<String> getProductName(@PathVariable Long productId) {
        String productName = orderService.getProductName(productId);
        if (productName != null) {
            return ResponseEntity.ok(productName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> orderCount() {
        return ResponseEntity.ok(orderService.orderCount());
    }
}
