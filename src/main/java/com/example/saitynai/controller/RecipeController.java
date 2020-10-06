package com.example.saitynai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.example.saitynai.exception.RecipeNotFoundException;
import com.example.saitynai.exception.UserNotFoundException;
import com.example.saitynai.model.Comment;
import com.example.saitynai.model.User;
import com.example.saitynai.repository.RecipeRepository;
import com.example.saitynai.model.Recipe;
import com.example.saitynai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = new ArrayList<Recipe>();
            recipeRepository.findAll().forEach(recipes::add);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{usersId}/recipes")
    public ResponseEntity<List<Recipe>> getRecipesByUserId(@PathVariable Long usersId) {
        try {
            if (!userRepository.existsById(usersId)) {
                throw new UserNotFoundException(usersId);
            }
            List<Recipe> recipes = recipeRepository.findByUserIdusers(usersId);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        }catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{usersID}/recipes/{recipesID}")
    public Recipe getRecipesByUserRecipeID(@PathVariable Long usersID, @PathVariable Long recipesID) {
        if (!userRepository.existsById(usersID)) {
            throw new UserNotFoundException(usersID);
        }
        Optional<Recipe> optRecipes = recipeRepository.findById(recipesID);
        if (optRecipes.isPresent() && optRecipes.get().getUser().getIdUsers() == usersID) {
            return optRecipes.get();
        } else {
            throw new RecipeNotFoundException(recipesID);
        }
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") long id) {
        Optional<Recipe> tutorialData = recipeRepository.findById(id);

        return tutorialData.map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //    public Recipe getRecipeByID(@PathVariable Long id) {
//        Optional<Recipe> optRecipe = recipeRepository.findById(id);
//        if(optRecipe.isPresent()) {
//            return optRecipe.get();
//        }else {
//            throw new RecipeNotFoundException(id);
//        }
//    }
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        try {
            Recipe _recipe = recipeRepository
                    .save(recipe);
            recipe.setUser(null);
            return new ResponseEntity<>(_recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("users/{userId}/recipes")
    public Recipe addRecipe(@PathVariable Long userId,
                              @Valid @RequestBody Recipe recipe) {
        return userRepository.findById(userId)
                .map(user -> {
                    recipe.setUser(user);
                    return recipeRepository.save(recipe);
                }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PutMapping("/recipes/{id}")
    public Recipe updateRecipe(@PathVariable Long id,
                               @Valid @RequestBody Recipe recipeUpdated) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setTitle(recipeUpdated.getTitle());
                    recipe.setIngredients(recipeUpdated.getIngredients());
                    recipe.setDescription(recipeUpdated.getDescription());
                    recipe.setRecipe(recipeUpdated.getRecipe());
                    recipe.setPublished(recipeUpdated.getPublished());
                    return recipeRepository.save(recipe);
                }).orElseThrow(() -> new RecipeNotFoundException(id));
    }
//    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") long id, @RequestBody Recipe recipe) {
//        Optional<Recipe> recipeData = recipeRepository.findById(id);
//
//        if (recipeData.isPresent()) {
//            Recipe _recipe = recipeData.get();
//            _recipe.setTitle(recipe.getTitle());
//            _recipe.setIngredients(recipe.getIngredients());
//            _recipe.setDescription(recipe.getDescription());
//            _recipe.setRecipe(recipe.getRecipe());
//            _recipe.setPublished(recipe.getPublished());
//            return new ResponseEntity<>(recipeRepository.save(_recipe), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/recipes/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipeRepository.delete(recipe);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new RecipeNotFoundException(id));
    }
//    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
//        try {
//            recipeRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/recipes/published")
    public ResponseEntity<List<Recipe>> findByPublished() {
        try {
            List<Recipe> recipes = recipeRepository.findByPublished(true);

            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}