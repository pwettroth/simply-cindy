package com.simplycindy.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class UserData {

    @Id
    @Email
    @NotNull(message = "Please enter valid email")
    private String email;

    @Column(name="isAdmin", columnDefinition="tinyint(1) default 0")
    @NotNull
    private boolean isAdmin;

    @NotNull(message = "Password was empty")
    @Size(min=6, message = "Password must contain at least 6 characters")
    private String password;

    @NotNull(message = "Please enter your first name")
    private String firstName;

    @NotNull(message = "Please enter your last name")
    private String lastName;

    public UserData(String email, boolean isAdmin, String password, String firstName, String lastName) {
        this.email = email;
        this.isAdmin = isAdmin;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
