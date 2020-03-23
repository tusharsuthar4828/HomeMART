package com.homemart.models;


public class Seller {

    private String username, email, password, imageURL;
    private Category category;
    private String phoneno;

    public Seller(String username, String email, String password, String imageURL, Category category, String phoneno) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
        this.category = category;
        this.phoneno = phoneno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String  getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}

