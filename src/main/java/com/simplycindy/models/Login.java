package com.simplycindy.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Login {

    @Id
    private int userId;

    @NotNull
    @Size(min=5, max =15, message = "Username must contain 5-15 characters")
    private String username;

    @NotNull
    @Size(min=6, message = "Password must contain at least 6 characters")
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login() {
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
}
