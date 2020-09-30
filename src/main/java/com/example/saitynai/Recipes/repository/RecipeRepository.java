package com.example.saitynai.Recipes.repository;

import com.example.saitynai.Recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByPublished(boolean published);
}
