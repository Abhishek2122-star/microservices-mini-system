//package com.example.order.controller;
//
//import com.example.order.entity.Order;
//import com.example.order.service.OrderService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final OrderService orderService;
//
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createOrder(@RequestBody Order order, Authentication auth) {
//        if (auth == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//        }
//        order.setUserEmail(auth.getName()); // ✅ ownership by JWT subject (email)
//        order.setStatus("CREATED");
//        return ResponseEntity.ok(orderService.createOrder(order));
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getOrders(Authentication auth) {
//        if (auth == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//        }
//        String role = auth.getAuthorities().iterator().next().getAuthority();
//        if (role.equals("ROLE_ADMIN")) {
//            return ResponseEntity.ok(orderService.getAllOrders());
//        } else {
//            return ResponseEntity.ok(orderService.getOrdersByUserEmail(auth.getName()));
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order order, Authentication auth) {
//        if (auth == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//        }
//        Order existing = orderService.getOrderById(id);
//        if (existing == null) {
//            return ResponseEntity.notFound().build();
//        }
//        String role = auth.getAuthorities().iterator().next().getAuthority();
//        if (!role.equals("ROLE_ADMIN") && !existing.getUserEmail().equals(auth.getName())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
//        }
//        order.setId(id);
//        order.setUserEmail(existing.getUserEmail()); // keep ownership unchanged
//        return ResponseEntity.ok(orderService.updateOrder(order));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteOrder(@PathVariable Long id, Authentication auth) {
//        if (auth == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//        }
//        Order existing = orderService.getOrderById(id);
//        if (existing == null) {
//            return ResponseEntity.notFound().build();
//        }
//        String role = auth.getAuthorities().iterator().next().getAuthority();
//        if (!role.equals("ROLE_ADMIN") && !existing.getUserEmail().equals(auth.getName())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
//        }
//        orderService.deleteOrder(id);
//        return ResponseEntity.noContent().build();
//    }
//}



package com.example.order.controller;

import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        order.setUserEmail(auth.getName()); // ✅ ownership by JWT subject (email)
        order.setStatus("CREATED");
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<?> getOrders(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        // ✅ Debug: print authorities to console
        System.out.println("Authorities: " + auth.getAuthorities());

        String role = auth.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_ADMIN")) {
            return ResponseEntity.ok(orderService.getAllOrders());
        } else {
            return ResponseEntity.ok(orderService.getOrdersByUserEmail(auth.getName()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody Order order, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        Order existing = orderService.getOrderById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // ✅ Debug: print authorities to console
        System.out.println("Authorities: " + auth.getAuthorities());

        String role = auth.getAuthorities().iterator().next().getAuthority();
        if (!role.equals("ROLE_ADMIN") && !existing.getUserEmail().equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
        }
        order.setId(id);
        order.setUserEmail(existing.getUserEmail()); // keep ownership unchanged
        return ResponseEntity.ok(orderService.updateOrder(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        Order existing = orderService.getOrderById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // ✅ Debug: print authorities to console
        System.out.println("Authorities: " + auth.getAuthorities());

        String role = auth.getAuthorities().iterator().next().getAuthority();
        if (!role.equals("ROLE_ADMIN") && !existing.getUserEmail().equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
        }
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}