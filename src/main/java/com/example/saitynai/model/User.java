package com.example.saitynai.model;

import com.example.saitynai.model.role.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusers;

    @NotBlank
    @Size(max = 20)
    private String username;

    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Recipe> recipe;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getIdUsers() {
        return this.idusers;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Role> getRoles(){
        return roles;
    }

    @JsonManagedReference(value = "recipe-user")
    public Set<Recipe> getRecipes() {
        return this.recipe;
    }

    public void setIdUsers(Long id) {
        this.idusers = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipe = recipes;
    }
}