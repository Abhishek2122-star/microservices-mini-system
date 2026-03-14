package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.client.UserClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public OrderController(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        Object user = userClient.getUserById(order.getUserId());

        if (user == null) {
            return "User not found. Cannot create order.";
        }

        orderRepository.save(order);
        return "Order created successfully for user " + order.getUserId();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }
}