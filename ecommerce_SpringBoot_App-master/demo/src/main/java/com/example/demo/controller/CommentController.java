package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

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
}
