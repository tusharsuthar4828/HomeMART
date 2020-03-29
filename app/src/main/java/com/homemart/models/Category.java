package com.homemart.models;

import java.util.ArrayList;
import java.util.List;

public class Category {

    Product product;

    public Category(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
