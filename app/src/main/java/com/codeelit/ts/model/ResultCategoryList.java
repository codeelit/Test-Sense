package com.codeelit.ts.model;

import java.io.Serializable;

public class ResultCategoryList implements Serializable {
    String category;

    public ResultCategoryList() {
    }

    public String getCategory() {
        return category;
    }

    public ResultCategoryList(String category) {
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
