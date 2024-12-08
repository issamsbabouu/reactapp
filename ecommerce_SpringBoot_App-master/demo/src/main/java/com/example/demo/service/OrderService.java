package com.example.demo.service;
import com.example.demo.modele.commande;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public void confirmDelivery(Long orderId) {
        commande order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setDelivered(true);
        orderRepository.save(order);
    }
}
