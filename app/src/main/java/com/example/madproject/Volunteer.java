package com.example.madproject;

public class Volunteer {
    private String name;
    private String email;
    private String phone;
    private String preferredArea; // Add this field

    private String docID;

    public Volunteer() {
        // Default constructor required for Firestore
    }

    public Volunteer(String name, String email, String phone, String preferredArea, String docID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.preferredArea = preferredArea;
        this.docID = docID;
    }

    // Getters and Setters
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

    public String getPreferredArea() {
        return preferredArea;
    }

    public void setPreferredArea(String preferredArea) {
        this.preferredArea = preferredArea;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
