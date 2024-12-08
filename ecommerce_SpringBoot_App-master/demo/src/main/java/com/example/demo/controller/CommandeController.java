package com.example.demo.controller;

import com.example.demo.modele.categorie;
import com.example.demo.modele.commande;
import com.example.demo.modele.comptes;
import com.example.demo.modele.produit;
import com.example.demo.service.CategorieService;
import com.example.demo.service.CommandeService;
import com.example.demo.service.ComptesService;
import com.example.demo.service.ProduitService;
import org.aspectj.bridge.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/commande")
public class CommandeController {
    @Autowired
    private ComptesService compteser;
    @Autowired
    private CommandeService commandeService;

    @GetMapping("/commande")
    public String getall(Model model) {
        List<commande> c = commandeService.getAllComm();
        model.addAttribute("all",c);
        List<comptes> deliveryman = compteser.findByType("deliveryman");
        model.addAttribute("delivery",deliveryman);
        // Récupérer le nombre total de commandes
        int countCommByfiliere = commandeService.countCommByfiliere("");
        model.addAttribute("commcount",""+countCommByfiliere);
        System.out.println(""+countCommByfiliere);
        int Architecture = commandeService.countCommByfiliere ("Architecture");
        model.addAttribute("Architecturecommande",""+Architecture);
        System.out.println("Architecturecommande"+Architecture);
        int CS = commandeService.countCommByfiliere ("CS");
        model.addAttribute("CScommande",""+CS);
        System.out.println("CScommande"+CS);
        int Automobile = commandeService.countCommByfiliere ("Automobile");
        model.addAttribute("Automobilecommande",""+Automobile);
        System.out.println("Automobilecommande"+Automobile);
        int Aerospace = commandeService.countCommByfiliere ("Aerospace");
        model.addAttribute("Aerospacecommande",""+Aerospace);
        System.out.println("Aerospacecommande"+Aerospace);
        int Medecine = commandeService.countCommByfiliere ("Medecine");
        model.addAttribute("Medecinecommande",""+Medecine);
        System.out.println("Medecinecommande"+Medecine);
        int Energy = commandeService.countCommByfiliere ("Energy");
        model.addAttribute("Energycommande",""+Energy);
        System.out.println("Energycommande"+Energy);
        return "dashboard_commandes";


    }

    @PostMapping("/assignCommand")
    public String modifyProduct(@RequestParam int id, @RequestParam int idD) {
        commandeService.affecterLivreur(id,idD);
        return "redirect:/commande/commande";
    }

    @GetMapping("/getcommliv")
    public String getPendingById( @RequestParam String username) {
        return "redirect:/commande/livreur/" + username;
    }

    @GetMapping("livreur/{username}")
    public String getAllProduits(Model model,  @PathVariable("username") String username) {
        List<commande> c = commandeService.findallbyusername(username);
        model.addAttribute("all",c);
        return "General_livreur";
    }




    @GetMapping("/formcom")
    public String getFormcom(Model model) {
        List<commande> c = commandeService.getAllComm();
        model.addAttribute("all",c);
        return "FormCommandes";
    }

}


