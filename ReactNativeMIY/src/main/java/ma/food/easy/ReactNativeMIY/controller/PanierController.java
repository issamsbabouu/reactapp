package ma.food.easy.ReactNativeMIY.controller;

import ma.food.easy.ReactNativeMIY.model.Panier;
import ma.food.easy.ReactNativeMIY.model.User;
import ma.food.easy.ReactNativeMIY.model.commande;
import ma.food.easy.ReactNativeMIY.model.produit;
import ma.food.easy.ReactNativeMIY.service.CommandeService;
import ma.food.easy.ReactNativeMIY.service.ComptesService;
import ma.food.easy.ReactNativeMIY.service.PanierService;
import ma.food.easy.ReactNativeMIY.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/panier")
public class PanierController {
    @Autowired
    private PanierService panierservice;
    @Autowired
    private CommandeService comm;
    @Autowired
    private ComptesService comptesService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private CommandeService cs;
    @Autowired
    private CommandeService commandeService;

    public PanierController(PanierService panierservice) {
        this.panierservice = panierservice;
    }

    //PANIER
    @GetMapping("/getpanier")
    public String getPanierById(@RequestParam int id, @RequestParam String username) {
        return "redirect:/panier/panier/" + id + "/" + username;
    }

    @GetMapping("/panier/{id}/{username}")
    public String getPanierByUsername(@PathVariable("id") int id, @PathVariable("username") String username, Model model) {
        Optional<User> categoryOptional = comptesService.getAccById((long) id);
        User c = categoryOptional.orElseThrow(() -> new RuntimeException("Compte not found"));
        List<commande> avoir = comm.findAllByPanierCompteEqualsAndConfirmed(c);
        double totalPrice = 0;
        for (commande command : avoir) {
            totalPrice += command.getQuantity() * command.getP().getPrice();
        }

        model.addAttribute("panier", avoir);
        model.addAttribute("totalPrice", totalPrice);
        return "Panier";
    }
    @GetMapping("/getpending")
    public String getPendingById(@RequestParam int id, @RequestParam String username) {
        return "redirect:/panier/pending/" + id + "/" + username;
    }

    @GetMapping("/pending/{id}/{username}")
    public String getPendingByUsername(@PathVariable("id") int id, @PathVariable("username") String username, Model model) {
        Optional<User> categoryOptional = comptesService.getAccById((long) id);
        User c = categoryOptional.orElseThrow(() -> new RuntimeException("Compte not found"));
        List<commande> avoir = comm.findAllByPanierCompteEquals(c);
        model.addAttribute("pending", avoir);
        return "Pending";
    }

    @GetMapping("/panier2")
    public String getPanierPage(Model model, String username) {
        Panier panier = new Panier();
        model.addAttribute("comptes", panier);
        return "panier";
    }
    @PostMapping("/delete")
    public String delete(Model model, @RequestParam int idCompte,@RequestParam int id, @RequestParam String username) {
        boolean isDeleted = false;
        try {
            isDeleted = commandeService.deleteProduitfromPanier(id);
            model.addAttribute("deleted", isDeleted);
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while deleting the product.");
        }
        return "redirect:/panier/panier/"+ idCompte + "/" + username;
    }

    @PostMapping("/addtocard")
    public String addtoCard(@RequestParam int quantity,@RequestParam int stock, @RequestParam int productId, @RequestParam int id, @RequestParam String username) {
        Optional<produit> produitOptional = produitService.getProduitById((long)productId);
        produit p = produitOptional.get();
        Optional<Panier> existingPanier = panierservice.findPanierByCompteId(id);
        Panier pan;
        if (existingPanier.isPresent()) {
            pan = existingPanier.get();
        } else {
            pan = new Panier();
            User c = new User();
            c.setId(id);
            pan.setCompte(c);
            panierservice.createPanier(pan);
        }
        commande comm = new commande();
        comm.setConfirmed(false);
        User c = new User();
        c.setId(id);
        comm.setQuantity(quantity);
        comm.setCompte(c);
        comm.setPanier(pan);
        comm.setP(p);
        comm.setConfirmed(false);
        comm.setDelivered(false);
        cs.createCommand(comm);
        return "redirect:/panier/panier/" + id + "/" + username;
    }
    @PostMapping("/addtocarde")
    public String ConfirmtoCommand(@RequestParam int stock,@RequestParam int quantity,@RequestParam int productId, @RequestParam String date, @RequestParam String lieu, @RequestParam List<Integer> idCommandes, @RequestParam int id, @RequestParam String username) {
        Optional<produit> produitOptional = produitService.getProduitById((long)productId);
        produit p = produitOptional.get();
        for (int idCommande : idCommandes) {
            Optional<commande> commandeOptional = commandeService.getCommandeById((long) idCommande);
            if (commandeOptional.isPresent()) {
                commande comm = commandeOptional.get();
                commandeService.updateDateAndLieu(comm.getId(), date, lieu, true);
                int a = stock-quantity;
                produitService.updateQuantity(p.getId(),a);
            }
        }
        return "redirect:/panier/pending/" + id + "/" + username;
    }

}
