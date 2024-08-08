package com.PK.astro;

import com.google.gson.annotations.SerializedName;

public class Zodiac {
    private String id;
    private String mainCatId;
    private String title;
    @SerializedName("tamil_name")
    private String tamilName;
    private String image;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainCatId() {
        return mainCatId;
    }

    public void setMainCatId(String mainCatId) {
        this.mainCatId = mainCatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTamilName() {
        return tamilName;
    }

    public void setTamilName(String tamilName) {
        this.tamilName = tamilName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
