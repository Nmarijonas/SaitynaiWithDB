package com.example.saitynai.repository;

import com.example.saitynai.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipeIdrecipes(Long recipeId);
}
