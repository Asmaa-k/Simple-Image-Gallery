package com.example.imagegallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse {

    @SerializedName("hits")
    @Expose()
    private List<Images> mImages;

    public List<Images> getImages() {
        return mImages;
    }
}
