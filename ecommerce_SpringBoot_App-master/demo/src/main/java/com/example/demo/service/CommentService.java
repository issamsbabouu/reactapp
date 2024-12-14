package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.ProduitDTO;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream().map(comment -> new CommentDTO(
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
    }
    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
    }
}
