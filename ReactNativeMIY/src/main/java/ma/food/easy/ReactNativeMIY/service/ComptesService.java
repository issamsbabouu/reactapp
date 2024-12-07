package ma.food.easy.ReactNativeMIY.service;

import ma.food.easy.ReactNativeMIY.model.User;
import ma.food.easy.ReactNativeMIY.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComptesService {
    public ComptesService(){}

    public ComptesService(UserRepository compteRepository){
        this.compteRepository=compteRepository;
    }
    @Autowired
    private UserRepository compteRepository;

    public List<User> getAllAccs() {
        return compteRepository.findAll();
    }
    public User findByUsername(String username){
        return compteRepository.findByUsername(username);
    }

    public Optional<User> getAccById(Long id) {
        return compteRepository.findById(id);
    }

    public User getAccountById(Long id) {
        return compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public User getAccountByusername(String username) {
        return compteRepository.findByUsername(username);
    }


    public boolean createCompte(User compte) {
        boolean res=false;
        try{
            compteRepository.save(compte);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    public User updatecompte(int id, User newaccountData ) {
        User existingAccount = getAccountById((long)id);
        existingAccount.setUsername(newaccountData.getUsername());
        existingAccount.setEmail(newaccountData.getEmail());
        existingAccount.setPassword(newaccountData.getPassword());
        existingAccount.setPhone(newaccountData.getPhone());
        return compteRepository.save(existingAccount);
    }

    public boolean updateCompte(int id, User updatedCompte) {
        boolean res=false;
        try{
            compteRepository.save(updatedCompte);
            updatedCompte.setId(id);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }

    public boolean deleteCompte(int id) {
        boolean res;
        if(compteRepository.existsById((long) id)) {
            compteRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public User authenticate(String username,String password){
        return compteRepository.findByEmailAndPassword(username,password);
    }
    public User trouverParNomUtilisateur(String username) {
        return compteRepository.findByUsername(username);
    }
    public int countcompte() {
        return (int) compteRepository.count();
    }
    public int countcompteByType(String type) {
        return compteRepository.countByType  (type);
    }
    public int countcompteByFiliere(String fill) {
        return compteRepository.countByFiliere (fill);
    }

    public List<User> findByType(String type){
        return compteRepository.findAllByType(type);
    }
}
