package com.homemart.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class CategoryToDisplay extends ExpandableGroup<Product> {

    String mCategoryName;
    List<Product> mProductsList;

    public CategoryToDisplay(String title, List<Product> items) {
        super(title, items);
    }


}
