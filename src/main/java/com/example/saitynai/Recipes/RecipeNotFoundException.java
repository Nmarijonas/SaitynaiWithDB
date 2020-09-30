package com.example.saitynai.Recipes;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Could not find recipe " + id);
    }
}
