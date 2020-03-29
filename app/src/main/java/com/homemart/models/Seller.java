package com.homemart.models;


public class Seller {

    private String username, email, password, imageURL, phoneno, description;
    private Categories categories;
    private int rating;

    public Seller(String username, String email, String password, String imageURL, String phoneno, String description, int rating) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
        this.phoneno = phoneno;
        this.description = description;
        this.rating = rating;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

