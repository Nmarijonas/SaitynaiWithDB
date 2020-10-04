package com.example.saitynai.controller;

import com.example.saitynai.exception.CommentNotFoundException;
import com.example.saitynai.exception.RecipeNotFoundException;
import com.example.saitynai.model.Comment;
import com.example.saitynai.repository.CommentRepository;
import com.example.saitynai.model.Recipe;
import com.example.saitynai.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
class CommentController {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("recipes/{recipeId}/comments")
    public List<Comment> getCommentsByRecipeId(@PathVariable Long recipeId) {

        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        return commentRepository.findByRecipeIdrecipes(recipeId);
    }

    @GetMapping("/recipes/{recipeId}/comments/{commentId}")
    public Comment getCommentByID(@PathVariable Long recipeId, @PathVariable Long commentId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        Optional<Comment> optComments = commentRepository.findById(commentId);
        if (optComments.isPresent() && optComments.get().getRecipe().getIdrecipes() == recipeId) {
            return optComments.get();
        } else {
            throw new CommentNotFoundException(commentId);
        }
    }

//    @GetMapping("/comments/{id}")
//    public ResponseEntity<Comment> getCommentById(@PathVariable("id") long id) {
//        Optional<Comment> commentData = commentRepository.findById(id);
//        return commentData.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK)).
//                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PostMapping("/recipes/{recipeId}/comments")
    public Comment addComment(@PathVariable Long recipeId,
                              @Valid @RequestBody Comment comment) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    comment.setRecipe(recipe);
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }
//    public ResponseEntity<Recipe> createComment(@PathVariable Long recipeId, @RequestBody Comment comment) {
//        try {
//            recipeRepository.findById(recipeId).map(recipe -> {
//                comment.setRecipe(recipe);
//                recipeRepository.save(recipe);
//                return new ResponseEntity<>(recipe, HttpStatus.CREATED);
//            });
//        } catch (Exception exception) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return  null;
//    }

    @PutMapping("/recipes/{recipeId}/comments/{commentId}")
    public Comment updateComment(@PathVariable("recipeId") long recipeId, @PathVariable("commentId") long commentId, @RequestBody Comment comment) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        Optional<Comment> optComments = commentRepository.findById(commentId);
        if (optComments.isPresent() && optComments.get().getRecipe().getIdrecipes() == recipeId) {
            return optComments
                    .map(newComment -> {
                        newComment.setTitle(comment.getTitle());
                        newComment.setComment(comment.getComment());
                        return commentRepository.save(newComment);
                    }).orElseThrow(() -> new CommentNotFoundException(commentId));
        } else {
            throw new CommentNotFoundException(commentId);
        }

//               Optional<Comment> commentData = commentRepository.findById(id);
//        if (commentData.isPresent()) {
//            Comment _comment = commentData.get();
//            _comment.setTitle(comment.getTitle());
//            _comment.setComment(comment.getComment());
//            return new ResponseEntity<>(commentRepository.save(_comment), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    @DeleteMapping("/recipes/{recipeId}/comments/{commentId}")
    public String deleteComment(@PathVariable Long recipeId, @PathVariable Long commentId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
        Optional<Comment> optComments = commentRepository.findById(commentId);
        if (optComments.isPresent() && optComments.get().getRecipe().getIdrecipes() == recipeId) {
            commentRepository.delete(optComments.get());
            return "Delete Successfully!";
        } else {
            throw new CommentNotFoundException(commentId);
        }
    }


//        return recipeRepository.findById(recipeId)
//                .map(recipe -> {
//                    commentRepository.findById(commentId).map(
//                            comment -> {
//                                commentRepository.delete(comment);
//                            }
//                    )
//                    recipeRepository.delete(recipe);
//                    return "Delete Successfully!";
//                }).orElseThrow(() -> new RecipeNotFoundException(id));
//    }
//    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
//        try {
//            commentRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}