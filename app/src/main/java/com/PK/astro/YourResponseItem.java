package com.PK.astro;

import com.google.gson.annotations.SerializedName;

public class YourResponseItem {
    @SerializedName("id")
    private String id;

    @SerializedName("main_catid")
    private String mainCatid;

    @SerializedName("img_path")
    private String imagepath;

    @SerializedName("sub_catid")
    private String subCatid;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("sub_name")
    private String subName;

    @SerializedName("title")
    private String title;

    @SerializedName("tamil_name")
    private String tamilName;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("pdf")
    private String pdf;

    @SerializedName("audio")
    private String audio;

    @SerializedName("pdf_path")
    private String pdfPath;

    @SerializedName("audio_path")
    private String audioPath; // Added audio_path field

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("updated_by")
    private String updatedBy;

    @SerializedName("is_status")
    private String isStatus;

    @SerializedName("is_deleted")
    private String isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainCatid() {
        return mainCatid;
    }

    public void setMainCatid(String mainCatid) {
        this.mainCatid = mainCatid;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getSubCatid() {
        return subCatid;
    }

    public void setSubCatid(String subCatid) {
        this.subCatid = subCatid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
