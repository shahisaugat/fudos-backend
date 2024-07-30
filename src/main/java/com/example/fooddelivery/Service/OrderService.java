package com.example.fooddelivery.Service;

import com.example.fooddelivery.Entity.Orders;

public interface OrderService {
    void placeOrder(Orders order);
    Orders getOrderById(Long id);
    Iterable<Orders> getAllOrders();
    String getProductName(Long furnitureId);
    Long orderCount();
}
