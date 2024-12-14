package com.example.demo.controller;
import com.example.demo.modele.*;
import com.example.demo.service.CommentService;
import com.example.demo.service.ProduitService;
import com.example.demo.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/comment")
public class CommentControllerImpl {
    private final CommentService commentService;
    private final ProduitService produitService;
    @Autowired
    public CommentControllerImpl(CommentService commentService, ProduitService produitService) {
        this.commentService = commentService;
        this.produitService = produitService;
    }
    @Autowired
    public RatingService ratingService;

    @GetMapping("/commentaires")
    public String afficherCommentaires(Model model) {
        model.addAttribute("commentaires", commentService.obtenirTousLesCommentaires());
        return "commentaires";
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
        Optional<Comment> comment = commentService.getCommentById((long) id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateComment(@PathVariable int id, @RequestBody Comment updatedComment) {
        boolean isUpdated = commentService.updateComment(id, updatedComment);
        if (isUpdated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(400).body(false);
        }
    }
    @PostMapping("/delete")
    public String delete(@RequestParam int id,Model model,@RequestParam String username) {
        boolean isDeleted=false;
        try {
            isDeleted = commentService.deleteComm(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/comment/mycomment/" + username;
    }
    @PostMapping("/deletecom")
    public String deletecom(@RequestParam int id,Model model,@RequestParam String username) {
        boolean isDeleted=false;
        try {
            isDeleted = commentService.deleteCommen(id);
            model.addAttribute("deleted", isDeleted);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/comment/count";
    }
    @GetMapping("/count")
    public String countComments(Model model) {
        int totalComments = commentService.countComments();
        model.addAttribute("totalComments", totalComments);

        String[] Filieres = {"Admin", "User", "Moderator"};
        for (String Filiere : Filieres) {
            int count = commentService.countByCompte_Filiere(Filiere);
            model.addAttribute(Filiere + "Count", count);
        }

        String[] Types = {"General", "Feedback", "Issue", "Suggestion"};
        for (String Type : Types) {
            int count = commentService.countByCompte_Type(Type);
            model.addAttribute(Type + "Count", count);
        }

        List<Comment> allComments = commentService.getAllComments();
        model.addAttribute("allComments", allComments);

        int all = commentService.countComments();
        model.addAttribute("count", "" + all);
        int architecture = commentService.countByCompte_Filiere("Architecture");
        model.addAttribute("Architecturstudents", "" + architecture);

        int cs = commentService.countByCompte_Filiere("CS");
        model.addAttribute("CSstudents", "" + cs);

        int energy = commentService.countByCompte_Filiere("Energy");
        model.addAttribute("Energystudents", "" + energy);

        int aerospace = commentService.countByCompte_Filiere("Aerospace");
        model.addAttribute("Aerospacestudents", "" + aerospace);

        int medecine = commentService.countByCompte_Filiere("Medecine");
        model.addAttribute("Medecinestudents", "" + medecine);

        int automobile = commentService.countByCompte_Filiere("Automobile");
        model.addAttribute("Automobilestudents", "" + automobile);

        int dentistry = commentService.countByCompte_Filiere("Dentistry");
        model.addAttribute("Dentistrystudents", "" + dentistry);

        return "dashboard_comments";
    }
    @GetMapping("/mycommentaires/produit/{produitId}")
    public String afficherCommentairesParProduitId(@PathVariable Long produitId, Model model) {
        List<Comment> commentaires = commentService.obtenirCommentairesParProduitId(produitId);
        model.addAttribute("commentaires", commentaires);
        return "catalogue";
    }
    @GetMapping("/mycomment/{username}")
    public  String getCommentByUsername(@PathVariable String username,Model model) {
        List<Comment> r = commentService.getCommentByUsername(username);
        model.addAttribute("com",r);
        return  "mycomments";
    }
}
