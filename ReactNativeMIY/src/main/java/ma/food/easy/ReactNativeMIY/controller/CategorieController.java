package ma.food.easy.ReactNativeMIY.controller;

import ma.food.easy.ReactNativeMIY.model.Categorie;
import ma.food.easy.ReactNativeMIY.service.CategorieService;
import ma.food.easy.ReactNativeMIY.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategorieController {
    @Autowired
    private CategorieService catService;
    @Autowired
    private ProduitService produitService;

    @GetMapping("/{id}")
    public Categorie getCategoryById(@PathVariable int id) {
        Optional<Categorie> c = catService.getCategoryById(id);
        return c.orElse(null);
    }

    @GetMapping("/categories")
    public String getAllCategories(Model model) {
        List<Categorie> c = catService.getAllCategories();
        model.addAttribute("allc", c);
        return "dashboard_categories";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String catname, Model model) {
        Categorie categorie = new Categorie();
        categorie.setNom(catname);
        boolean isCategoryCreated = catService.createCategorie(categorie);

        if (isCategoryCreated) {
            return "redirect:/categories/categories";
        } else {
            System.out.println("exists already");
            model.addAttribute("errorMessage", "Category already exists!");
        }
        return "redirect:/categories/categories";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam int idC) {
        boolean isDeleted = catService.deleteCategorie(idC);
        if (isDeleted) {
            System.out.println("categ deleted successfully.");
        } else {
            System.out.println("Failed to delete categ.");
        }
        return "redirect:/categories/categories";
    }
}
