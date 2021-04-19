package com.example.tourme;

public class PostModel {

    String imageId,imageUrl,imageTitle,imageDescrip,email;

    public PostModel(String imageId, String imageUrl, String imageTitle, String imageDescrip,String email) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
        this.imageDescrip = imageDescrip;
        this.email=email;
    }

    public String getImageId() {
        return imageId;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getImageDescrip() {
        return imageDescrip;
    }
}

