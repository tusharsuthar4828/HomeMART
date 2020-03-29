package com.homemart.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    //@SerializedName(value="cake",alternate={"chocolates", "masala","cookies","waffers","pickle","papad","icecream","dietfood"})
    Category category;

    public Categories(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
