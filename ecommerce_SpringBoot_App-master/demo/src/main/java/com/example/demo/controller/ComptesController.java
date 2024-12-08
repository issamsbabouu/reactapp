package com.example.demo.controller;
import com.example.demo.modele.comptes;
import com.example.demo.modele.produit;
import com.example.demo.service.CommandeService;
import com.example.demo.service.ComptesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/comptes")
public class ComptesController {
    @Autowired
    private ComptesService daoComptes;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private ComptesService comptesService;
    public ComptesController(ComptesService daoComptes) {
        this.daoComptes = daoComptes;
    }
    @GetMapping
    public  List<comptes> getAllAccs() {return daoComptes.getAllAccs();
    }
    @GetMapping("/{id}")
    public comptes getComptesById(@PathVariable int id) {
        Optional<comptes> comptes = daoComptes.getAccById((long)id);
        return comptes.orElse(null);
    }
    @PutMapping("/{id}")
    public boolean updateComptes(@PathVariable int id, @RequestBody comptes updatedComptes) {
        return daoComptes.updateCompte(id, updatedComptes);
    }
    @DeleteMapping("/{id}")
    public boolean deleteComptes(@PathVariable int id) {
        return daoComptes.deleteCompte(id);
    }



    @GetMapping("/information")
    public String getProfilePae(Model model, @RequestParam String username) {
        return "redirect:/comptes/profile/"+username;
    }

    @GetMapping("/informationLivreur")
    public String getProfilePaeL(Model model, @RequestParam String username) {
        return "redirect:/comptes/profileLivreur/"+username;
    }

    @GetMapping("/informationAdmin")
    public String getProfilePaeA(Model model, @RequestParam String username) {
        return "redirect:/comptes/profileAdmin/"+username;
    }
    @GetMapping("/profile/{username}")
    public String getProfilePage(Model model, @PathVariable("username") String username) {
        comptes compte = daoComptes.findByUsername(username);
        model.addAttribute("comptes", compte);
        return "compte";
    }
    @GetMapping("/profileAdmin/{username}")
    public String getProfilePage2(Model model, @PathVariable("username") String username) {
        comptes compte = daoComptes.findByUsername(username);
        model.addAttribute("comptes", compte);
        return "dashboard_accInformation";
    }
    @GetMapping("/profileLivreur/{username}")
    public String getProfilePage3(Model model, @PathVariable("username") String username) {
        comptes compte = daoComptes.findByUsername(username);
        model.addAttribute("comptes", compte);
        return "profilelivreur";
    }
    @GetMapping("/count")
    public String countCompte(Model model) {
        // Récupérer le nombre total de comptes
        int countCompte = daoComptes.countcompte();
        model.addAttribute("compteCount",""+countCompte);
        System.out.println(""+countCompte);
        int Delivery = daoComptes.countcompteByType ("DeliveryMan");
        model.addAttribute("DeliveryMan",""+Delivery);
        System.out.println("DeliveryMan"+Delivery);
        int Supplier = daoComptes.countcompteByType ("Supplier");
        model.addAttribute("Supplier",""+Supplier);
        System.out.println("Supplier"+Supplier);
        int Student = daoComptes.countcompteByType ("Student");
        model.addAttribute("Student",""+Student);
        System.out.println("Student"+Student);
        int Admin = daoComptes.countcompteByType ("Admin");
        model.addAttribute(" Admin",""+Admin);
        System.out.println("Admin"+Admin);
        int Architecture = daoComptes.countcompteByFiliere ("Architecture");
        model.addAttribute("Architecturstudents",""+Architecture);
        System.out.println("Architecturstudents"+Architecture);
        int CS = daoComptes.countcompteByFiliere ("CS");
        model.addAttribute("CSstudents",""+CS);
        System.out.println("CSstudents"+CS);
        int Energy = daoComptes.countcompteByFiliere ("Energy");
        model.addAttribute("Energystudents",""+Energy);
        System.out.println("Energystudents"+Energy);
        int Aerospace = daoComptes.countcompteByFiliere ("Aerospace");
        model.addAttribute("Aerospacestudents",""+Aerospace);
        System.out.println("Aerospacestudents"+Aerospace);
        int Medicine = daoComptes.countcompteByFiliere ("Medecine");
        model.addAttribute("Medicinestudents",""+Medicine);
        System.out.println("Medicinestudents"+Medicine);
        int Automobile = daoComptes.countcompteByFiliere ("Automobile");
        model.addAttribute("Automobilestudents",""+Automobile);
        System.out.println("Automobile"+Automobile);
        List<comptes> comptes = daoComptes.getAllAccs();
        model.addAttribute("all",comptes);
        return "dashboard_accounts";
    }
    @PostMapping("/addcompteuser")
    public String addProduct( @RequestParam int phone, @RequestParam String fullname,@RequestParam String emailadress,
                              @RequestParam String photo, @RequestParam String username, @RequestParam String password, @RequestParam String field) {
        comptes account = new comptes();
        account.setType("Student");
        account.setFiliere(field);
        account.setEmail(emailadress);
        account.setUsername(username);
        account.setNom(fullname);
        account.setPhone(phone);
        account.setPhoto(photo);
        daoComptes.createCompte(account);
        return "redirect:/comptes/count";
    }
    @PostMapping("/addcompteforothers")
    public String addProductall( @RequestParam int phone, @RequestParam String fullname,@RequestParam String emailadress,
                                 @RequestParam String username, @RequestParam String password,@RequestParam String type, @RequestParam String photo) {
        comptes account = new comptes();
        account.setType(type);
        account.setPassword(password);
        account.setEmail(emailadress);
        account.setUsername(username);
        account.setNom(fullname);
        account.setPhone(phone);
        account.setPhoto(photo);
        daoComptes.createCompte(account);
        return "redirect:/comptes/count";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam int id,Model model) {
        try {
            boolean isDeleted = comptesService.deleteCompte(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/comptes/count";
    }

    @PostMapping("/modify")
    public String modifycompte(@ModelAttribute comptes account) {
        comptesService.updatecompte(account.getId(),account);
        return "redirect:/log/login";
    }

    @PostMapping("/modifyAdmin")
    public String modifycompte2(@ModelAttribute comptes account) {
        comptesService.updatecompte(account.getId(),account);
        return "redirect:/log/login";
    }

    @PostMapping("/modifyLivreur")
    public String modifycompte3(@ModelAttribute comptes account) {
        comptesService.updatecompte(account.getId(),account);
        return "redirect:/log/login";
    }

    @GetMapping("/contact")
    public String getByType(Model model){
        List<comptes> supplier = comptesService.findByType("Supplier");
        List<comptes> delivery = comptesService.findByType("DeliveryMan");
        model.addAttribute("supplier",supplier);
        model.addAttribute("delivery",delivery);
        return "dashboard_contact";
    }
}