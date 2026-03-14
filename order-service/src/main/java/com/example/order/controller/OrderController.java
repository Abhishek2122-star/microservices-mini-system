//package com.example.order.controller;
//
//import com.example.order.entity.Order;
//import com.example.order.repository.OrderRepository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final OrderRepository orderRepository;
//
//    public OrderController(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }
//
//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    @PostMapping
//    public Order createOrder(@RequestBody Order order) {
//        return orderRepository.save(order);
//    }
//
//    @GetMapping("/{id}")
//    public Order getOrderById(@PathVariable Long id) {
//        return orderRepository.findById(id).orElse(null);
//    }
//
//    @PutMapping("/{id}")
//    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
//        Order order = orderRepository.findById(id).orElse(null);
//        if (order != null) {
//            order.setUserId(orderDetails.getUserId());
//            order.setProduct(orderDetails.getProduct());
//            order.setQuantity(orderDetails.getQuantity());
//            order.setStatus(orderDetails.getStatus());
//            return orderRepository.save(order);
//        }
//        return null;
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteOrder(@PathVariable Long id) {
//        orderRepository.deleteById(id);
//        return "Order deleted successfully";
//    }
//}


package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderController(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        // Call User Service to validate user
        String userServiceUrl = "http://localhost:8081/users/" + order.getUserId();
        Object user = restTemplate.getForObject(userServiceUrl, Object.class);

        if (user == null) {
            return "User not found. Cannot create order.";
        }

        orderRepository.save(order);
        return "Order created successfully for user " + order.getUserId();
    }
}