package com.PK.astro;

import com.google.gson.annotations.SerializedName;

public class AboutItem {

    @SerializedName("id")
    private String id;

    @SerializedName("pdf")
    private String pdfFileName;

    @SerializedName("pdf_path")
    private String pdfPath;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("cat_name")
    private String categoryName;

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
