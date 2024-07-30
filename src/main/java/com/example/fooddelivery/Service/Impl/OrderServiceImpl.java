package com.example.fooddelivery.Service.Impl;


import com.example.fooddelivery.Entity.OrderItem;
import com.example.fooddelivery.Entity.Orders;

import com.example.fooddelivery.Entity.Product;

import com.example.fooddelivery.Repository.OrderRepo;
import com.example.fooddelivery.Repository.ProductRepo;
import com.example.fooddelivery.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepository;
    private final ProductRepo productRepository;




    @Override
    public void placeOrder(Orders order) {
        List<OrderItem> orderItems = order.getCartItems().stream().map(item -> {
            Product product = productRepository.findById(item.getProduct().getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + item.getProduct().getProductId()));
            item.setProduct(product);
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setCartItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(Math.toIntExact(id)).orElse(null);
    }

    @Override
    public Iterable<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String getProductName(Long productId) {
        return productRepository.findById(productId).map(Product::getProductName).orElse(null);
    }

    @Override
    public Long orderCount() {
        return orderRepository.count();
    }
}
