package com.example.saitynai.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private Recipe recipe;

    public Comment(){}
    public Comment(String title, String comment){
        this.title = title;
        this.comment = comment;
    }
    public Long getIdcomment(){
        return this.id;
    }
    public String getTitle(){return this.title;}
    public String getComment(){return this.comment;}
    @JsonBackReference(value = "recipe-comment")
    public Recipe getRecipe(){return this.recipe;}

    public void setIdComment(Long id) {
        this.id = id;
    }
    public void setTitle(String title){this.title = title;}
    public void setComment(String comment){this.comment = comment;}
    public void setRecipe(Recipe recipe){this.recipe=recipe;}
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Comment))
            return false;
        Comment comment = (Comment) o;
        return Objects.equals(this.id, comment.id)
                && Objects.equals(this.title, comment.title)
                && Objects.equals(this.comment,comment.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.comment);
    }

    @Override
    public String toString() {
        return "Comment{" + "" +
                "id=" + this.id + ", "+
                "title='" + this.title + '\'' + ", "+
                "comment='" + this.comment + '\'' + '}';
    }
}