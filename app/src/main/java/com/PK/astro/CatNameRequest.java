package com.PK.astro;
public class CatNameRequest {
    private String cat_name;

    public CatNameRequest(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCatName() {
        return cat_name;
    }

    public void setCatName(String cat_name) {
        this.cat_name = cat_name;
    }
}
