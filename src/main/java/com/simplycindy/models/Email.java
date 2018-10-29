package com.simplycindy.models;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class Email {

    @NotNull(message = "Please enter name")
    private String name;

    @NotNull(message = "Please enter email")
    private String email;

    @NotNull(message = "Please enter message")
    private String message;

    private MultipartFile image;

    public Email(String name, String email, String message, MultipartFile image) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.image = image;
    }

    public Email() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
