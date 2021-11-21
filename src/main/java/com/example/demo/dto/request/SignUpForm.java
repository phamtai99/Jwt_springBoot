package com.example.demo.dto.request;

import com.example.demo.model.Role;

import java.util.Set;

public class SignUpForm {
    private String name;
    private String username;
    private String password;
    private String email;
    Set<String> roles;

    public SignUpForm() {
    }

    public SignUpForm(String name, String username, String password, String email, Set<String> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
