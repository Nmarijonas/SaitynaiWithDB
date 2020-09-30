package com.example.saitynai.Users.repository;

import com.example.saitynai.Users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
