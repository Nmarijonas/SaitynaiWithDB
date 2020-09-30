package com.example.saitynai.Recipes.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="recipes")
public class Recipe {
    @Id @GeneratedValue private Long idrecipes;
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
    //private List<Comment> comments;

    public Recipe(){}
    public Recipe(String title, String ingredients, String description, String recipe){
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.recipe = recipe;
    }
    public Long getIdrecipes(){
        return this.idrecipes;
    }
    public String getTitle(){return this.title;}
    public String getIngredients(){return this.ingredients;}
    public String getDescription(){return this.description;}
    public String getRecipe(){return this.recipe;}
    public Boolean getPublished() {return this.published;}

    public void setIdrecipes(Long id) {
        this.idrecipes = id;
    }
    public void setTitle(String title){this.title = title;}
    public void setIngredients(String ingredients){this.ingredients = ingredients;}
    public void setDescription(String description){this.description = description;}
    public  void setRecipe(String recipe){this.recipe=recipe;}
    public void  setPublished(Boolean published){this.published = published;}
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