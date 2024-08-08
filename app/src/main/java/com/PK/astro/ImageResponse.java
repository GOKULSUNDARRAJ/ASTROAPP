package com.PK.astro;

public class ImageResponse {
    private String id;
    private String image;
    private String img_path;
    private String created_at;
    private String created_by;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getImg_path() { return img_path; }
    public void setImg_path(String img_path) { this.img_path = img_path; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public String getCreated_by() { return created_by; }
    public void setCreated_by(String created_by) { this.created_by = created_by; }
}
