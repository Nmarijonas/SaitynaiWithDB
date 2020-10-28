package com.example.saitynai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.example.saitynai.exception.RecipeNotFoundException;
import com.example.saitynai.exception.UserNotFoundException;
import com.example.saitynai.model.User;
import com.example.saitynai.repository.RecipeRepository;
import com.example.saitynai.model.Recipe;
import com.example.saitynai.repository.UserRepository;
import com.example.saitynai.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    // Access permit to all
    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = new ArrayList<Recipe>(recipeRepository.findAll());
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Access permit to all
    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") long id) {
        Optional<Recipe> tutorialData = recipeRepository.findById(id);

        return tutorialData.map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Access permit to all
    @GetMapping("/users/{usersId}/recipes")
    public ResponseEntity<List<Recipe>> getRecipesByUserId(@PathVariable Long usersId) {
        try {
            if (!userRepository.existsById(usersId)) {
                throw new UserNotFoundException(usersId);
            }
            List<Recipe> recipes = recipeRepository.findByUserIdusers(usersId);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Access permit to all
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("users/{id}/recipes")
    public ResponseEntity<Recipe> addRecipe(@PathVariable Long id,
                                            @Valid @RequestBody Recipe recipe) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long userId = userDetails.getId();
        Optional<User> userData = userRepository.findById(userId);
        if (userId == id && userData.isPresent()) {
            recipe.setUser(userData.get());
            return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("users/{userId}/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long userId, @PathVariable Long id,
                                               @Valid @RequestBody Recipe recipeUpdated) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long _userId = userDetails.getId();
        Optional<User> userData = userRepository.findById(userId);
        if (_userId == userId && userData.isPresent()) {
            return recipeRepository.findById(id)
                    .map(recipe -> {
                        recipe.setTitle(recipeUpdated.getTitle());
                        recipe.setIngredients(recipeUpdated.getIngredients());
                        recipe.setDescription(recipeUpdated.getDescription());
                        recipe.setRecipe(recipeUpdated.getRecipe());
                        recipe.setPublished(recipeUpdated.getPublished());
                        return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.OK);
                    }).orElseThrow(() -> new RecipeNotFoundException(id));
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("users/{userId}/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable Long userId, @PathVariable Long id) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long _userId = userDetails.getId();
        Optional<User> userData = userRepository.findById(userId);
        if (_userId == userId && userData.isPresent()) {
            try {
                recipeRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        throw new UserNotFoundException(userId);
    }

    // Access permit to all
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