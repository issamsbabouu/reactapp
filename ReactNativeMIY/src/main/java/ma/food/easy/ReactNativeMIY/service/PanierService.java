package ma.food.easy.ReactNativeMIY.service;

import ma.food.easy.ReactNativeMIY.model.Panier;
import ma.food.easy.ReactNativeMIY.repository.PanierRepository;
import ma.food.easy.ReactNativeMIY.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PanierService {
    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private ProduitService produitService;


    public Optional<Panier> getPanierById(Long id) {
        return panierRepository.findById((Long)id);
    }

    public boolean deleteProduitfromPanier(int id) {
        boolean res;
        if(panierRepository.existsById((long) id)) {
            panierRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public boolean createPanier(Panier panier) {
        boolean res=false;
        try{
            panierRepository.save(panier);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    public Optional<Panier> findPanierByCompteId(int id) {
        return panierRepository.findByCompteId(id);
    }
}
