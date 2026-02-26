package com.example.painter;



import java.util.List;

public class DataModel {
    private String name;
    private String qualification;
    private String phoneNumber;
    private List<String> imageUrls;

    public DataModel(String name, String qualification, String phoneNumber, List<String> imageUrls) {
        this.name = name;
        this.qualification = qualification;
        this.phoneNumber = phoneNumber;
        this.imageUrls = imageUrls;
    }

    public String getName() {
        return name;
    }

    public String getQualification() {
        return qualification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}

