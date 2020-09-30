package com.example.saitynai.Users.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue private Long user_id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    public User(){}
    public User(String name,String password){
        this.name = name;
        this.password = password;
    }
    public Long getIdUsers(){
        return this.user_id;
    }
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}

    public void setIdUsers(Long id) {
        this.user_id = id;
    }
    public void setName(String name){this.name = name;}
    public void setPassword(String password) {this.password = password;}
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User recipe = (User) o;
        return Objects.equals(this.user_id, recipe.user_id)
                && Objects.equals(this.name, recipe.name)
                && Objects.equals(this.password, recipe.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.user_id, this.name, this.password);
    }

    @Override
    public String toString() {
        return "Recipe{" + "" +
                "id=" + this.user_id + ", "+
                "name='" + this.name + '\'' + ", "+
                "password='" + this.password + '\'' + '}';
    }
}