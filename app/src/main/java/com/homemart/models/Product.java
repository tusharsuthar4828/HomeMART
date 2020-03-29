package com.homemart.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String description;
    private String imageURL;
    private String name;
    private int price;
    private String unit;
    private int foodtype;
    private String key;
    private String producttype;


    public Product(String description, String imageURL, String name, int price, String unit, int foodtype) {
        this.description = description;
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.foodtype = foodtype;
    }

    public Product(String description, String imageURL, String name, int price, String unit, int foodtype, String key, String producttype) {
        this.description = description;
        this.imageURL = imageURL;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.foodtype = foodtype;
        this.key = key;
        this.producttype = producttype;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(int foodtype) {
        this.foodtype = foodtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
