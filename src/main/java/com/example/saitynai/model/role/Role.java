package com.example.saitynai.model.role;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idroles;

    @Enumerated(EnumType.STRING)
    private  RoleEnum name;

    public int getRoleId() {
        return idroles;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setRoleId(int idroles) {
        this.idroles = idroles;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
