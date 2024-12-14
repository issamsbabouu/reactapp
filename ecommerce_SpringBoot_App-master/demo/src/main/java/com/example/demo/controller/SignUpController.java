package com.example.demo.controller;

import com.example.demo.DTO.SignUpDTO;
import com.example.demo.DTO.CompteDTO;
import com.example.demo.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apii/signuping")
public class SignUpController {

    @Autowired
    private ComptesService compteService;

    @PostMapping
    public ResponseEntity<CompteDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            CompteDTO compteDTO = compteService.signUp(signUpDTO);
            return ResponseEntity.ok(compteDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<CompteDTO> signUpp(@RequestBody SignUpDTO signUpDTO) {
        try {
            CompteDTO compteDTO = compteService.signUppi(signUpDTO);
            return ResponseEntity.ok(compteDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
