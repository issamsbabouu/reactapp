package ma.food.easy.ReactNativeMIY.service;

import ma.food.easy.ReactNativeMIY.model.User;
import ma.food.easy.ReactNativeMIY.model.commande;
import ma.food.easy.ReactNativeMIY.repository.CommandeRepository;
import ma.food.easy.ReactNativeMIY.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private UserRepository compteRepository;
    @Autowired
    private ComptesService comptesService;

    public int count(User c){
        return commandeRepository.countCommandesByCompte(c);
    }

    public List<commande> getAllComm() {
        return commandeRepository.findAll();
    }

    public double calculateTotalBenefit() {
        List<commande> commands = commandeRepository.findAllByDelivered(true);
        double totalBenefit = 0.0;
        for (commande command : commands) {
            double commandBenefit = command.getP().getPrice() * command.getQuantity();
            totalBenefit += commandBenefit;
        }
        return totalBenefit;
    }
    public double calculateTotalBenefitPerMonth(String month) {
        double totalBenefit = 0.0;
        List<commande> commands = commandeRepository.findAll(); // Assume this method fetches all commands
        for (commande command : commands) {
            try {
                String dateLivraison = command.getDateLivraison();
                if (dateLivraison == null) {
                    // Skip this command and continue with the next one
                    continue;
                }
                LocalDate date = LocalDate.parse(dateLivraison, DateTimeFormatter.ISO_DATE);
                if (date.getMonthValue() == Integer.parseInt(month)) {
                    if (command.isDelivered()) {
                        double commandBenefit = command.getP().getPrice() * command.getQuantity();
                        totalBenefit += commandBenefit;
                    }
                }
            } catch (DateTimeParseException e) {
                // Handle the exception if the date format is incorrect
                e.printStackTrace();
            }
        }
        return totalBenefit;
    }


    public double calculateTotalBenefitPerYear(int year) {
        double totalBenefit = 0.0;
        List<commande> commands = commandeRepository.findAll(); // Assuming this method fetches all commands

        for (commande command : commands) {
            try {
                String dateLivraison = command.getDateLivraison();
                if (dateLivraison == null) {
                    // Skip this command and continue with the next one
                    continue;
                }

                LocalDate date = LocalDate.parse(dateLivraison, DateTimeFormatter.ISO_DATE);
                int commandYear = date.getYear();

                if (commandYear == year && command.isDelivered()) {
                    double commandBenefit = command.getP().getPrice() * command.getQuantity();
                    totalBenefit += commandBenefit;
                }
            } catch (Exception e) {
                // Handle parsing errors or other exceptions
                e.printStackTrace(); // Log the exception for debugging
            }
        }
        return totalBenefit;
    }
    public boolean deleteProduitfromPanier(int id) {
        boolean res;
        if(commandeRepository.existsById((long) id)) {
            commandeRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public int countCommByfiliere(String filiere){
        return commandeRepository.countByCompte_Filiere(filiere);
    }

    public commande getCommandById(Long id) {
        return commandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Optional<commande> getCommandeById(Long id) {
        return commandeRepository.findById((Long)id);
    }

    public commande affecterLivreur(int id, int idD) {
        commande existingCommand = getCommandById((long)id);
        Optional<User> compteOpt = comptesService.getAccById((long)idD);
        User c = compteOpt.get();
        existingCommand.setDeliveryman(c);
        return commandeRepository.save(existingCommand);
    }


    public boolean createCommand(commande commande) {
        boolean res=false;
        try{
            commandeRepository.save(commande);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }

    public List<commande> findallbyusername(String username){
        return commandeRepository.findAllByDeliverymanUsername(username);
    }


    public List<commande> findAllByPanierCompteEqualsAndConfirmed(User compte) {
        return commandeRepository.findAllByPanierCompteEqualsAndConfirmed(compte, false);
    }

    public List<commande> findAllByPanierCompteEquals(User compte) {
        return commandeRepository.findAllByPanierCompteEquals(compte);
    }

    public commande updateDateAndLieu(int id, String date, String lieu, Boolean confirmed) {
        commande existingProduct = getCommandById((long)id);
        commande comm = new commande();
        comm.setDateLivraison(date);
        comm.setLieuLivraison(lieu);
        comm.setConfirmed(confirmed);
        existingProduct.setDateLivraison(comm.getDateLivraison());
        existingProduct.setLieuLivraison(comm.getLieuLivraison());
        existingProduct.setConfirmed(comm.getConfirmed());
        return commandeRepository.save(existingProduct);
    }

}
