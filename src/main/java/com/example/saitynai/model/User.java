package com.example.saitynai.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusers;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "role_id")
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Recipe> recipe;

    public User(){}
    public User(String name,String password, String role){
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public Long getIdUsers(){
        return this.idusers;
    }
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}
    public String getRole(){return this.role;}
    @JsonManagedReference(value = "recipe-user")
    public Set<Recipe> getRecipes(){return this.recipe;}


    public void setIdUsers(Long id) {
        this.idusers = id;
    }
    public void setName(String name){this.name = name;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role){this.role = role;}
    public void setRecipes(Set<Recipe> recipes){this.recipe = recipes;}
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User recipe = (User) o;
        return Objects.equals(this.idusers, recipe.idusers)
                && Objects.equals(this.name, recipe.name)
                && Objects.equals(this.password, recipe.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idusers, this.name, this.password);
    }

    @Override
    public String toString() {
        return "Recipe{" + "" +
                "id=" + this.idusers + ", "+
                "name='" + this.name + '\'' + ", "+
                "password='" + this.password + '\'' + '}';
    }
}