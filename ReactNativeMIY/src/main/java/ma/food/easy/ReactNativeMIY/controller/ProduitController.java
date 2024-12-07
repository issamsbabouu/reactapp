package ma.food.easy.ReactNativeMIY.controller;
import ma.food.easy.ReactNativeMIY.model.*;
import ma.food.easy.ReactNativeMIY.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/products")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private CategorieService catService;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ComptesService daoComptes;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/catalogue")
    public String getAllProduits(Model model) {
        List<produit> produits = produitService.getAllProduits();
        Map<Integer, Integer> productRatings = new HashMap<>(); // Map to store product ratings

        for (produit p : produits) {
            int avgRating = ratingService.getAvg(p.getId());
            productRatings.put(p.getId(), avgRating);
        }

        model.addAttribute("ratings", productRatings);
        model.addAttribute("all", produits);
        List<Categorie> c = catService.getAllCategories();
        model.addAttribute("allc", c);
        return "shop";
    }

    @GetMapping("/catalogueForAll")
    public String getAllProduitsForAll(Model model) {
        List<produit> produits = produitService.getAllProduits();
        Map<Integer, Integer> productRatings = new HashMap<>(); // Map to store product ratings

        for (produit p : produits) {
            int avgRating = ratingService.getAvg(p.getId());
            productRatings.put(p.getId(), avgRating);
        }

        model.addAttribute("ratings", productRatings);
        model.addAttribute("all", produits);
        List<Categorie> c = catService.getAllCategories();
        model.addAttribute("allc", c);
        return "catalogue";
    }


    @GetMapping("/viewdetails")
    public String details(@RequestParam int id) {
        // Redirect to the singleproduct endpoint with the provided ID
        return "redirect:/products/singleproduct/" + id;
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String label, @RequestParam int price, @RequestParam String color,
                             @RequestParam String photo, @RequestParam String size, @RequestParam int categoryId, @RequestParam int stock,@RequestParam String description) {
        // Fetch the category object by ID
        Optional<Categorie> categoryOptional = catService.getCategoryById(categoryId);

        Categorie category = categoryOptional.get();

        // Create a product object and set its properties
        produit product = new produit();
        product.setLabel(label);
        product.setPrice(price);
        product.setColor(color);
        product.setPhoto(photo);
        product.setSize(size);
        product.setQuantity(stock);
        product.setDescription(description);

        // Set the category for the product
        product.setCategorie(category);

        // Save the product to the database
        produitService.createProduit(product);

        // Redirect to the desired page
        return "redirect:/products/products";
    }

    @PostMapping("/modify")
    public String modifyProduct(@ModelAttribute produit product,@RequestParam int categoryid) {
        produitService.updateProduit(product.getId(), product, categoryid);
        return "redirect:/products/products";
    }


    @GetMapping("/singleproduct/{id}")
    public String getSingleProduct(Model model, @PathVariable("id") int id) {
        Optional<produit> optionalProduct = produitService.getProduitById((long)id);
        List<Comment> comment= commentService.getAllCommentsById(id);
        model.addAttribute("comment", comment);
        List<Rating> rating=ratingService.getAllRatingsById(id);
        model.addAttribute("rating",rating);
        // Check if the product exists
        if (optionalProduct.isPresent()) {
            produit product = optionalProduct.get();
            model.addAttribute("oneproduct", product);
            return "command";
        }
        else {
            return "error";
        }
    }


    @PostMapping("/delete")
    public String delete(@RequestParam int id,Model model) {
        boolean isDeleted=false;
        try {
            isDeleted = produitService.deleteProduit(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/products/products";
    }


    @GetMapping("/products")
    public String countProducts(Model model) {
        //All products
        int productCount = produitService.countProduits();
        model.addAttribute("productCount", ""+productCount);
        //Archi
        int countArchi = produitService.countProduitsByCategorie("Archi");
        model.addAttribute("archi", ""+countArchi);
        //Auto
        int countAuto = produitService.countProduitsByCategorie("Auto");
        model.addAttribute("auto", ""+countAuto);
        //Aero
        int countAero = produitService.countProduitsByCategorie("Aero");
        model.addAttribute("aero", ""+countAero);
        //CS
        int countCS = produitService.countProduitsByCategorie("CS");
        model.addAttribute("cs", ""+countCS);
        //Dentaire
        int countDentistry = produitService.countProduitsByCategorie("Dentistry");
        model.addAttribute("dentistry", ""+countDentistry);
        //Medecine
        int countMedecine = produitService.countProduitsByCategorie("Medecine");
        model.addAttribute("medecine", ""+countMedecine);
        //Energie
        int countEnergie = produitService.countProduitsByCategorie("Energy");
        model.addAttribute("energie", ""+countEnergie);
        //Générale
        int countAll = produitService.countProduitsByCategorie("None");
        model.addAttribute("nocat", ""+countAll);
        //getall
        List<produit> produits = produitService.getAllProduits();
        model.addAttribute("all",produits);
        List<Categorie> c = catService.getAllCategories();
        model.addAttribute("allc",c);


        List<String> productNames = new ArrayList<>();
        List<Double> productRatings = new ArrayList<>();

        /*for (produit produit : produits) {
            productNames.add(produit.getLabel());
            productRatings.add(produit.getRating());
        }*/

        model.addAttribute("productNames", productNames);
        model.addAttribute("productRatings", productRatings);
        return "dashboard_products";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        //All products
        int productCount = produitService.countProduits();
        model.addAttribute("productCount", ""+productCount);
        //Archi
        int countArchi = produitService.countProduitsByCategorie("Archi");
        model.addAttribute("archi", ""+countArchi);
        //Auto
        int countAuto = produitService.countProduitsByCategorie("Auto");
        model.addAttribute("auto", ""+countAuto);
        //Aero
        int countAero = produitService.countProduitsByCategorie("Aero");
        model.addAttribute("aero", ""+countAero);
        //CS
        int countCS = produitService.countProduitsByCategorie("CS");
        model.addAttribute("cs", ""+countCS);
        //Dentaire
        int countDentistry = produitService.countProduitsByCategorie("Dentistry");
        model.addAttribute("dentistry", ""+countDentistry);
        //Medecine
        int countMedecine = produitService.countProduitsByCategorie("Medecine");
        model.addAttribute("medecine", ""+countMedecine);
        //Energie
        int countEnergie = produitService.countProduitsByCategorie("Energy");
        model.addAttribute("energie", ""+countEnergie);
        //Générale
        int countAll = produitService.countProduitsByCategorie("None");
        model.addAttribute("nocat", ""+countAll);
        //getall
        List<produit> produits = produitService.getAllProduits();
        model.addAttribute("all",produits);
        List<Categorie> c = catService.getAllCategories();
        model.addAttribute("allc",c);

        //getRatings
        List<String> productNames = new ArrayList<>();
        /*List<Double> productRatings = new ArrayList<>();
        /*for (produit produit : produits) {
            productNames.add(produit.getLabel());
            productRatings.add(produit.getRating());
        }
        model.addAttribute("productNames", productNames);
        model.addAttribute("productRatings", productRatings);*/

        //getStock
        List<Integer> productQuantity = new ArrayList<>();
        for (produit produit : produits) {
            productNames.add(produit.getLabel());
            productQuantity.add(produit.getQuantity());
        }
        model.addAttribute("productQuantity", productQuantity);

        //getStock
        List<User> comptes = daoComptes.getAllAccs();
        List<String> accountName = new ArrayList<>();
        List<Integer> numOfCommands = new ArrayList<>();

        for (User acc : comptes) {
            int com = commandeService.count(acc);
            accountName.add(acc.getNom());
            numOfCommands.add(com);
        }

        model.addAttribute("accNames", accountName);
        model.addAttribute("numOfCommands", numOfCommands);


        //getTypes
        int Delivery = daoComptes.countcompteByType("DeliveryMan");
        model.addAttribute("DeliveryMan",""+Delivery);
        int Supplier = daoComptes.countcompteByType("Supplier");
        model.addAttribute("Supplier",""+Supplier);
        int Student = daoComptes.countcompteByType("Student");
        model.addAttribute("Student",""+Student);
        int Admin = daoComptes.countcompteByType("Admin");
        model.addAttribute(" Admin",""+Admin);


        //fields
        int Architecture = daoComptes.countcompteByFiliere ("Architecture");
        model.addAttribute("Architecturstudents",""+Architecture);

        int CS = daoComptes.countcompteByFiliere ("CS");
        model.addAttribute("CSstudents",""+CS);

        int Energy = daoComptes.countcompteByFiliere ("Energy");
        model.addAttribute("Energystudents",""+Energy);
        int Aerospace = daoComptes.countcompteByFiliere ("Aerospace");
        model.addAttribute("Aerospacestudents",""+Aerospace);

        int Medicine = daoComptes.countcompteByFiliere ("Medecine");
        model.addAttribute("Medicinestudents",""+Medicine);

        int Automobile = daoComptes.countcompteByFiliere ("Automobile");
        model.addAttribute("Automobilestudents",""+Automobile);

        //benefit
        double chiffreAffaires=commandeService.calculateTotalBenefit();
        model.addAttribute("CA",""+chiffreAffaires);

        //benefitPerMonth

        double january = commandeService.calculateTotalBenefitPerMonth("01");
        model.addAttribute("january", "" + january);

        double february = commandeService.calculateTotalBenefitPerMonth("02");
        model.addAttribute("february", "" + february);

        double march = commandeService.calculateTotalBenefitPerMonth("03");
        model.addAttribute("march", "" + march);

        double april = commandeService.calculateTotalBenefitPerMonth("04");
        model.addAttribute("april", "" + april);

        double may = commandeService.calculateTotalBenefitPerMonth("05");
        model.addAttribute("may", "" + may);

        double june = commandeService.calculateTotalBenefitPerMonth("06");
        model.addAttribute("june", "" + june);

        double july = commandeService.calculateTotalBenefitPerMonth("07");
        model.addAttribute("july", "" + july);

        double august = commandeService.calculateTotalBenefitPerMonth("08");
        model.addAttribute("august", "" + august);

        double september = commandeService.calculateTotalBenefitPerMonth("09");
        model.addAttribute("september", "" + september);

        double october = commandeService.calculateTotalBenefitPerMonth("10");
        model.addAttribute("october", "" + october);

        double november = commandeService.calculateTotalBenefitPerMonth("11");
        model.addAttribute("november", "" + november);

        double december = commandeService.calculateTotalBenefitPerMonth("12");
        model.addAttribute("december", "" + december);


        //benefitPerYear
        double twentyfour= commandeService.calculateTotalBenefitPerYear(2024);
        model.addAttribute("twentyfour", "" + twentyfour);
        double twentythree= commandeService.calculateTotalBenefitPerYear(2023);
        model.addAttribute("twentythree", "" + twentythree);

        int totalRatings = 0; // Total ratings for all products
        int totalProducts = 0; // Total number of products
        List<Integer> productRatings = new ArrayList<>();

        for (produit p : produits) {
            int avgRating = ratingService.getAvg(p.getId());
            totalRatings += avgRating;
            totalProducts++;
            productNames.add(p.getLabel()); // Assuming 'getName()' gets the product's name
            productRatings.add(avgRating);
        }

// Calculate average rating for all products
        int avgRatingForAllProducts = (totalProducts > 0) ? totalRatings / totalProducts : 0;

        model.addAttribute("rating", avgRatingForAllProducts);
        model.addAttribute("productNames", productNames);
        model.addAttribute("productRatings", productRatings);

        return "dashboard_analytics";
    }


}
