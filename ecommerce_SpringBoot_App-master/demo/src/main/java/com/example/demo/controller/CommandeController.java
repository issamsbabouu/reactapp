package com.example.demo.controller;

import com.example.demo.DTO.CommandeDTO;
import com.example.demo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // Endpoint to create an order
    @PostMapping
    public ResponseEntity<CommandeDTO> createOrder(@RequestBody CommandeDTO commandeDTO) {
        try {
            // Call service to create the order
            CommandeDTO createdOrder = commandeService.createOrder(commandeDTO);

            // Return success response with the created order details
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
