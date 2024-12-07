package main.java.com.example.controller;

import main.java.com.example.service.ClientService;
import main.java.com.example.dto.ClientRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientRegistrationDto clientDto) {
        try {
            clientService.register(clientDto);
            return new ResponseEntity<>("Client registered successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
