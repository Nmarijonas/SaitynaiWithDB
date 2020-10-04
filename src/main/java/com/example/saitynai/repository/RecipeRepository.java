package com.example.saitynai.repository;

import com.example.saitynai.model.Comment;
import com.example.saitynai.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByPublished(boolean published);
    List<Recipe> findByUserIdusers(Long userId);
}
