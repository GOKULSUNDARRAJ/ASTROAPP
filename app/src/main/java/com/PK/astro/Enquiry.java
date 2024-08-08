package com.PK.astro;

public class Enquiry {
    private String name;
    private String email;
    private String phone;
    private String meassage;

    public Enquiry(String name, String email, String phone, String meassage) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.meassage = meassage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMeassage() {
        return meassage;
    }

    public void setMeassage(String meassage) {
        this.meassage = meassage;
    }
}
