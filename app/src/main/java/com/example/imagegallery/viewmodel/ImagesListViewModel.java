package com.example.imagegallery.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.imagegallery.model.Images;
import com.example.imagegallery.repository.ImageRepository;

import java.util.List;

public class ImagesListViewModel extends ViewModel {
    //No Application To Be Passed

    ImageRepository repository;
    boolean mIsViewingImages;

    public ImagesListViewModel() {
        mIsViewingImages = false;
        repository = ImageRepository.getInstance();
    }

    public void searchImagesApi(String query)
    {
        mIsViewingImages = true;
        repository.searchImagesApi(query);
    }

    public MutableLiveData<List<Images>> getImages() {
        return repository.getImages();
    }

    public boolean IsViewingImages() {
        return mIsViewingImages;
    }

    public void setViewingImages(boolean mIsViewingImages) {
        this.mIsViewingImages = mIsViewingImages;
    }


    public boolean onBackPressed(){
        if(mIsViewingImages){
            mIsViewingImages = false;
            return false;
        }
        return true;
    }
}
