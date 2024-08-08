package com.PK.astro;
public class AboutUsResponse {
    private String id;
    private String pdf;
    private String pdf_path;
    private String created_at;
    private String created_by;
    private String cat_name;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPdf() { return pdf; }
    public void setPdf(String pdf) { this.pdf = pdf; }

    public String getPdfPath() { return pdf_path; }
    public void setPdfPath(String pdf_path) { this.pdf_path = pdf_path; }

    public String getCreatedAt() { return created_at; }
    public void setCreatedAt(String created_at) { this.created_at = created_at; }

    public String getCreatedBy() { return created_by; }
    public void setCreatedBy(String created_by) { this.created_by = created_by; }

    public String getCatName() { return cat_name; }
    public void setCatName(String cat_name) { this.cat_name = cat_name; }
}
