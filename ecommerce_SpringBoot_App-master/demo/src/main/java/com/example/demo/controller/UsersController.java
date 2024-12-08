package com.example.demo.controller;
import com.example.demo.modele.categorie;
import com.example.demo.modele.comptes;
import com.example.demo.modele.produit;
import com.example.demo.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/log")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @PostMapping("/login")
    public String authenticateUser(String email, String password, Model model, HttpSession session) {
        comptes compte = usersService.authenticate(email, password);
        if (compte != null) {
            model.addAttribute("this",compte);
            session.setAttribute("authenticatedUsername", compte.getUsername());
            session.setAttribute("authenticatedPhoto", compte.getPhoto());
            session.setAttribute("authenticatedId", compte.getId());
            System.out.println(session);
            model.addAttribute("authenticatedUser", compte);
            if (compte.getType().equalsIgnoreCase("Student")) {
                return "redirect:/products/catalogue";
            } else if (compte.getType().equalsIgnoreCase("Admin")) {
                return "redirect:/products/products";
            } else if (compte.getType().equalsIgnoreCase("Deliveryman")) {
                return "redirect:/commande/livreur/" + compte.getUsername();
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "loginPage";
        }
        return "loginPage";
    }
    @GetMapping("/comptes/{username}")
    public String obtenirUtilisateurParNom(@PathVariable String nomUtilisateur, Model model) {
        comptes username = usersService.trouverParNomUtilisateur(nomUtilisateur);
        model.addAttribute("utilisateur", username);
        return "";
    }


    //aboutus
    @GetMapping("/apropos")
    public String apropos() {
        return "aboutus";
    }
    //home
    @GetMapping("/home")
    public String home() {
        return "homePage";
    }
}
