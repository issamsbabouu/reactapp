package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.ProduitDTO;
import com.example.demo.modele.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import com.example.demo.service.ComptesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private ComptesService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    // Method to delete a comment by its ID
    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content if the deletion is successful
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the comment is not found
        }
    }
    @GetMapping("/user")
    public ResponseEntity<List<CommentDTO>> getCommentsForUser(HttpSession session) {
        Long compteId = (Long) session.getAttribute("compteId");

        if (compteId == null) {
            return ResponseEntity.status(401).body(null);  // Utilisateur non connecté
        }

        // Récupérer les commentaires de l'utilisateur connecté
        List<Comment> comments = commentService.getCommentsByUser(compteId);
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCompte().getUsername(),
                new ProduitDTO(
                        comment.getProduit().getId(),
                        comment.getProduit().getLabel(),
                        comment.getProduit().getPrice(),
                        comment.getProduit().getPhoto()
                )
        )).collect(Collectors.toList());

        return ResponseEntity.ok(commentDTOs);
    }

}
