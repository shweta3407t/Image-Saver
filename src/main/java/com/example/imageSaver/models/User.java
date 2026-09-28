package com.example.imageSaver.models;

import jakarta.persistence.*;


import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false , unique = true)
    private  String userName;

    @Column(nullable = false , length = 200)
    private  String password;


    @ManyToMany
    @JoinTable(name = "user_role"  ,
            joinColumns = @JoinColumn(name =  "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();





    private  boolean enabled = true;


    public User(){}

    public User(Long id, String userName, String password, Set<Role> roles, boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
