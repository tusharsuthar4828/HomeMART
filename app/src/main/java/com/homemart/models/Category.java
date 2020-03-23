package com.homemart.models;

public class Category {
    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category(Product product) {
        this.product = product;
    }
}
