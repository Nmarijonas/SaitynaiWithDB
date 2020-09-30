package com.example.saitynai.Comments;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("Could not find comment " + id);
    }
}
