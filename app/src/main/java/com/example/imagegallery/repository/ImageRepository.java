package com.example.imagegallery.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.imagegallery.model.Images;
import com.example.imagegallery.requests.ImageApiClient;

import java.util.List;

public class ImageRepository {
    private static ImageRepository instance;
    ImageApiClient apiClient;

    ImageRepository() {
        apiClient = ImageApiClient.getInstance();
    }

    public static ImageRepository getInstance() {
        if (instance == null)
            instance = new ImageRepository();
        return instance;
    }

    public void searchImagesApi(String query) {
        apiClient.searchImagesApi(query);
    }

    public MutableLiveData<List<Images>> getImages() {
        return apiClient.getmImages();
    }
}
