package com.example.saitynai.controller;

import com.example.saitynai.exception.CommentNotFoundException;
import com.example.saitynai.exception.RecipeNotFoundException;
import com.example.saitynai.exception.UserNotFoundException;
import com.example.saitynai.model.Comment;
import com.example.saitynai.model.User;
import com.example.saitynai.repository.CommentRepository;
import com.example.saitynai.model.Recipe;
import com.example.saitynai.repository.RecipeRepository;
import com.example.saitynai.repository.UserRepository;
import com.example.saitynai.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
class CommentController {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    // Access permit to all
    @GetMapping("recipes/{recipeId}/comments")
    public List<Comment> getCommentsByRecipeId(@PathVariable Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        return commentRepository.findByRecipeIdrecipes(recipeId);
    }

    @GetMapping("recipes/{recipeId}/comments/{commentId}/owner")
    public Long getCommentOwner(@PathVariable Long recipeId, @PathVariable Long commentId) {
        if (recipeRepository.existsById(recipeId) &&
                commentRepository.findById(commentId).isPresent()) {
            return commentRepository.findById(commentId).get().getUser().getIdUsers();
        } else {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    // Access permit to all
    @GetMapping("/recipes/{recipeId}/comments/{commentId}")
    public Comment getCommentByID(@PathVariable Long recipeId, @PathVariable Long commentId) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long userId = userDetails.getId();
        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        Optional<Comment> optComments = commentRepository.findById(commentId);
        if (optComments.isPresent() && optComments.get().getRecipe().getIdrecipes().equals(recipeId)) {
            return optComments.get();
        } else {
            throw new CommentNotFoundException(commentId);
        }
    }

    @GetMapping("users/{userId}/recipes/{recipeId}/comments")
    public List<Comment> getCommentsByUserIdANDRecipeId(@PathVariable Long userId, @PathVariable Long recipeId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        return commentRepository.findByRecipeIdrecipes(recipeId);
    }

    @GetMapping("users/{userId}/recipes/{recipeId}/comments/{commentId}")
    public Optional<Comment> getOneCommentByUserIdANDRecipeId(@PathVariable Long userId, @PathVariable Long recipeId, @PathVariable Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        } else if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        } else if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException(commentId);
        }
        return commentRepository.findById(commentId);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @PostMapping("users/{userId}/recipes/{recipeId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long userId, @PathVariable Long recipeId,
                                              @Valid @RequestBody Comment comment) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        long _userId = userDetails.getId();
        Optional<User> _userData = userRepository.findById(_userId);
        Optional<User> userData = userRepository.findById(userId);
        Optional<Recipe> recipeData = recipeRepository.findById(recipeId);
        if (_userData.isPresent() && userData.isPresent() && recipeData.isPresent()) {
            comment.setUser(_userData.get());
            comment.setRecipe(recipeData.get());
            return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
        } else {
            if (!userData.isPresent()) {
                throw new UserNotFoundException(userId);
            } else {
                throw new RecipeNotFoundException(recipeId);
            }
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("users/{userId}/recipes/{recipeId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("userId") long userId, @PathVariable("recipeId") long recipeId, @PathVariable("commentId") long commentId, @RequestBody Comment newComment) {
        Optional<User> userData = userRepository.findById(userId);
        Optional<Recipe> recipeData = recipeRepository.findById(recipeId);
        Optional<Comment> commentData = commentRepository.findById(commentId);

        if (userData.isPresent() &&
                recipeData.isPresent() && commentData.isPresent()) {
            return commentRepository.findById(commentId)
                    .map(comment -> {
                        comment.setTitle(newComment.getTitle());
                        comment.setComment(newComment.getComment());
                        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
                    }).orElseThrow(() -> new CommentNotFoundException(commentId));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("users/{userId}/recipes/{recipeId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long userId, @PathVariable Long recipeId, @PathVariable Long commentId) {
        Optional<User> userData = userRepository.findById(userId);
        Optional<Recipe> recipeData = recipeRepository.findById(recipeId);
        Optional<Comment> commentData = commentRepository.findById(commentId);
        if (userData.isPresent() &&
                recipeData.isPresent() && commentData.isPresent()) {
            commentRepository.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}