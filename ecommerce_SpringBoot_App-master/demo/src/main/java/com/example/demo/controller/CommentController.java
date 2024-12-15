package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
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
    }@GetMapping("/user")
    public ResponseEntity<List<Comment>> getCommentsForUser(HttpSession session) {
        Long compteId = (Long) session.getAttribute("compteId");

        // Log to see if the user is authenticated correctly (session value)
        System.out.println("User ID from session: " + compteId);

        if (compteId == null) {
            // User is not logged in or session expired
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Fetch comments from the database
        List<Comment> comments = commentService.getCommentsByUser(compteId);

        // Log the fetched comments
        System.out.println("Fetched comments: " + comments);

        if (comments.isEmpty()) {
            // Return a 204 No Content if no comments exist
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(comments);
    }



}
