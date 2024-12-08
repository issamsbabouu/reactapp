package com.example.demo.service;
import com.example.demo.modele.categorie;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.modele.produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {
    @Autowired
    private CategorieRepository categorieRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    public ProduitService() {}
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CategorieService catService;

    public List<produit> getAllProduits() {
        return produitRepository.findAll();
    }

        public Optional<produit> getProduitById(Long id) {
        return produitRepository.findById((Long)id);
    }

    public boolean createProduit(produit produit) {
        boolean res=false;
        try{
            produitRepository.save(produit);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }


    public produit getProductById(Long id) {
        return produitRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public produit updateProduit(int id, produit newProductData, int categoryId) {
        produit existingProduct = getProductById((long)id);
        Optional<categorie> categoryOptional = catService.getCategoryById(categoryId);

        categorie category = categoryOptional.get();
            existingProduct.setLabel(newProductData.getLabel());
            existingProduct.setDescription(newProductData.getDescription());
            existingProduct.setQuantity(newProductData.getQuantity());
            existingProduct.setPrice(newProductData.getPrice());
            existingProduct.setColor(newProductData.getColor());
            existingProduct.setSize(newProductData.getSize());
            existingProduct.setCategorie(category);

            existingProduct.setPhoto(newProductData.getPhoto());
            return produitRepository.save(existingProduct);
    }

    public produit updateQuantity(int id, int quantity) {
        produit existingProduct = getProductById((long)id);
        produit p = new produit();
        p.setQuantity(quantity);
        existingProduct.setQuantity(p.getQuantity());
        return produitRepository.save(existingProduct);
    }

    public boolean deleteProduit(int id) {
        boolean res;
        if(produitRepository.existsById((long) id)) {
            produitRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }

    public int countProduits() {
        return (int) produitRepository.count();
    }

    public int countProduitsByCategorie(String cat) {
        return produitRepository.countByCategorie_Catname(cat);
    }


}
