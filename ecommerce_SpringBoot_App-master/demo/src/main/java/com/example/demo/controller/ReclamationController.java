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
        List<produit> p = produitService.getAllProduits();
        model.addAttribute("produits", p);
        model.addAttribute("reclam",r);
        return  "reclamation";
    }
    @GetMapping("/viewdetails")
    public String details(@RequestParam String username) {

        return "redirect:/reclamation/myreclamation/" + username;
    }

    @PostMapping("/addReclamation")
    public String addReclamation(@RequestParam String proof,@RequestParam String username,@RequestParam String description, @RequestParam int idCompte,@RequestParam int productId) {
        Optional<produit> produitOptional = produitService.getProduitById((long)productId);
        produit p = produitOptional.get();
        Reclamation complaint = new Reclamation();
        complaint.setdescription(description);
        comptes compte = new comptes();
        complaint.setCompte(compte);
        complaint.setProof(proof);
        complaint.setProduit(p);
        compte.setId(idCompte);
        complaint.setCompte(compte);
        ReclamationSer.createReclamation(complaint);
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

    @GetMapping("/count")
    public String countcomplaint(Model model) {
        int countcomplaint = ReclamationSer.countcomplaint();
        model.addAttribute("countcomplaint", "" + countcomplaint);
        System.out.println("" + countcomplaint);
        int Delivery = ReclamationSer.countByCompte_Type ("DeliveryMan");
        model.addAttribute("DeliveryMan",""+Delivery);
        System.out.println("DeliveryMan"+Delivery);
        int Supplier = ReclamationSer.countByCompte_Type ("Supplier");
        model.addAttribute("Supplier",""+Supplier);
        System.out.println("Supplier"+Supplier);
        int Student = ReclamationSer.countByCompte_Type ("Student");
        model.addAttribute("Student",""+Student);
        System.out.println("Student"+Student);

        int Admin = ReclamationSer.countByCompte_Type ("Admin");
        model.addAttribute(" Admin",""+Admin);
        System.out.println("Admin"+Admin);
        int Architecture = ReclamationSer.countByCompte_Filiere ("Architecture");
        model.addAttribute("Architecturstudents",""+Architecture);
        System.out.println("Architecturstudents"+Architecture);
        int CS = ReclamationSer.countByCompte_Filiere ("CS");
        model.addAttribute("CSstudents",""+CS);
        System.out.println("CSstudents"+CS);
        int Energy = ReclamationSer.countByCompte_Filiere ("Energy");
        model.addAttribute("Energystudents",""+Energy);
        System.out.println("Energystudents"+Energy);
        int Aerospace = ReclamationSer.countByCompte_Filiere ("Aerospace");
        model.addAttribute("Aerospacestudents",""+Aerospace);
        System.out.println("Aerospacestudents"+Aerospace);
        int Medecine = ReclamationSer.countByCompte_Filiere ("Medecine");
        model.addAttribute("Medecinestudents",""+Medecine);
        System.out.println("Medecinestudents"+Medecine);
        int Automobile = ReclamationSer.countByCompte_Filiere ("Automobile");
        model.addAttribute("Automobilestudents",""+Automobile);
        System.out.println("Automobile"+Automobile);
        int Dentistry = ReclamationSer.countByCompte_Filiere ("Dentistry");
        model.addAttribute("Dentistrystudents",""+Dentistry);
        System.out.println("Dentistry"+Dentistry);
        List<Reclamation> reclamations = ReclamationSer.getAllReclamations();
        model.addAttribute("all",reclamations);
        return "dashboard_complaints";
    }
}
