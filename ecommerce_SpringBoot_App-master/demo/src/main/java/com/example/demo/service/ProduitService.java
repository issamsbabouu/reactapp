package com.example.demo.service;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.modele.produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class ProduitService {

    @Autowired
    private ProduitRepository productRepository;

    public List<produit> getAllProducts() {
        return productRepository.findAll();  // Récupère tous les produits
    }

    public Optional<produit> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public produit saveProduct(produit product) {
        return productRepository.save(product);  // Sauvegarde un produit
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);  // Supprime un produit
    }

    public Optional<produit> getProduitById(long productId) {
        return Optional.empty();
    }

    public void updateQuantity(int id, int a) {
    }

    public List<produit> getAllProduits() {
        return List.of();
    }
}
