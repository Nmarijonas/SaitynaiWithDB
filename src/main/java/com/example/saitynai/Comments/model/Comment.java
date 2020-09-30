package com.example.saitynai.Comments.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="comments")
public class Comment {
    @Id @GeneratedValue private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "comment")
    private String comment;
    //private List<Comment> comments;

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

    public void setIdcomment(Long id) {
        this.id = id;
    }
    public void setTitle(String title){this.title = title;}
    public void setComment(String comment){this.comment = comment;}
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
        return "Recipe{" + "" +
                "id=" + this.id + ", "+
                "title='" + this.title + '\'' + ", "+
                "comment='" + this.comment + '\'' + '}';
    }
}