package com.example.saitynai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="recipes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idrecipes;
    @Column(name = "title")
    private String title;
    @Column(name = "ingredients")
    private String ingredients;
    @Column(name = "description")
    private String description;
    @Column(name = "recipe")
    private String recipe;
    @Column(name="published")
    private boolean published;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> comments;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Recipe(){}
    public Recipe(String title, String ingredients, String description, String recipe, boolean published){
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.recipe = recipe;
        this.published = published;
    }
    public Long getIdrecipes(){
        return this.idrecipes;
    }
    public String getTitle(){return this.title;}
    public String getIngredients(){return this.ingredients;}
    public String getDescription(){return this.description;}
    public String getRecipe(){return this.recipe;}
    public Boolean getPublished() {return this.published;}
    @JsonManagedReference(value = "recipe-comment")
    public Set<Comment> getComments(){return this.comments;}
    @JsonBackReference(value = "recipe-user")
    public User getUser(){return this.user;}

    public void setIdrecipes(Long id) {
        this.idrecipes = id;
    }
    public void setTitle(String title){this.title = title;}
    public void setIngredients(String ingredients){this.ingredients = ingredients;}
    public void setDescription(String description){this.description = description;}
    public  void setRecipe(String recipe){this.recipe=recipe;}
    public void  setPublished(Boolean published){this.published = published;}
    public void setComments(Set<Comment> comments){this.comments = comments;}
    public void setUser(User user){this.user = user;}
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Recipe))
            return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(this.idrecipes, recipe.idrecipes)
                && Objects.equals(this.title, recipe.title)
                && Objects.equals(this.ingredients, recipe.ingredients)
                && Objects.equals(this.description,recipe.description)
                && Objects.equals(this.recipe,recipe.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idrecipes, this.title, this.ingredients,this.description,this.recipe);
    }

    @Override
    public String toString() {
        return "Recipe{" + "" +
                "id=" + this.idrecipes + ", "+
                "title='" + this.title + '\'' + ", "+
                "ingredients='" + this.ingredients + '\'' + ", "+
                "description='" + this.description + '\'' + ", "+
                "recipe='" + this.recipe + '\'' + '}';
    }
}