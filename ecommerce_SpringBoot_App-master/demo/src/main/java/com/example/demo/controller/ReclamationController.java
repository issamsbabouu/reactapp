package com.example.demo.controller;

import com.example.demo.modele.Reclamation;
import com.example.demo.modele.categorie;
import com.example.demo.modele.comptes;
import com.example.demo.modele.produit;
import com.example.demo.service.ProduitService;
import com.example.demo.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reclamation")
public class ReclamationController {

    @Autowired
    private ReclamationService ReclamationSer;
    @Autowired
    private ReclamationService reclamationService;
    @Autowired
    private ProduitService produitService;

    public ReclamationController(ReclamationService reclamationService) {
        this.ReclamationSer = reclamationService;
    }

    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return ReclamationSer.getAllReclamations();
    }

    @GetMapping("/myreclamation/{username}")
    public  String getReclamationsByUsername(@PathVariable String username,Model model) {
        List<Reclamation> r = ReclamationSer.getReclamationsByUsername(username);
        List<produit> p = produitService.getAllProducts();
        model.addAttribute("produits", p);
        model.addAttribute("reclam",r);
        return  "reclamation";
    }
    @GetMapping("/viewdetails")
    public String details(@RequestParam String username) {

        return "redirect:/reclamation/myreclamation/" + username;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int idReclam,Model model,@RequestParam String usernamee) {
        boolean isDeleted=false;
        try {
            isDeleted = reclamationService.deleteReclam(idReclam);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/reclamation/myreclamation/" + usernamee;
    }
    @PostMapping("/deletebyAdmin")
    public String deleteByAdmin(@RequestParam int id,Model model) {
        boolean isDeleted=false;
        try {
            isDeleted = reclamationService.deleteReclam(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/reclamation/count";
    }

}
