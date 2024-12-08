package com.example.demo.service;

import com.example.demo.modele.Comment;
import com.example.demo.modele.Reclamation;
import com.example.demo.modele.produit;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentService() {
        this.commentRepository = null; // Default constructor for some frameworks
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public List<Comment> getAllCommentsById(int id) {
        return commentRepository.findAllByProduitId(id);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }


    public boolean createComment(Comment comment) {
        boolean res=false;
        try{
            commentRepository.save(comment);
            res=true;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return res;
    }
    public boolean updateComment(int id, Comment updatedComment) {
        boolean res = false;
        try {
            updatedComment.setId((long) id);
            commentRepository.save(updatedComment);
            res = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }
    public boolean deleteComm(int id) {
        boolean res;
        if(commentRepository.existsById((long) id)) {
            commentRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public boolean deleteCommen(int id) {
        boolean res;
        if(commentRepository.existsById((long) id)) {
            commentRepository.deleteById((long) id);
            res=true;
        }
        else{

            res=false;
        }
        return res;
    }
    public int countComments() {
        return (int) commentRepository.count();
    }

    public int countByCompte_Filiere(String Filiere) {
        return commentRepository.countByCompte_Filiere(Filiere);
    }

    public int countByCompte_Type(String Type) {
        return commentRepository.countByCompte_Type(Type);
    }



    public List<Comment> obtenirCommentairesParProduitId(Long produitId) {
        return commentRepository.findByProduitId(produitId);
    }

    public List<Comment> obtenirTousLesCommentaires() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentByUsername(String username) {
        return commentRepository.findAllByCompteUsername(username); // Return null if reclamation is not found
    }

}