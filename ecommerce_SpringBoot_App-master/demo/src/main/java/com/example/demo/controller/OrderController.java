package com.example.demo.controller;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/confirm-delivery/{orderId}")
    public ResponseEntity<String> confirmDelivery(@PathVariable Long orderId) {
        try {
            orderService.confirmDelivery(orderId);
            return new ResponseEntity<>("Delivery confirmed", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error confirming delivery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
