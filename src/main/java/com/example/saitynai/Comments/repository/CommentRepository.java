package com.example.saitynai.Comments.repository;

import com.example.saitynai.Comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
