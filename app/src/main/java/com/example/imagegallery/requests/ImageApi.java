package com.example.imagegallery.requests;

import com.example.imagegallery.model.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageApi {
    // SEARCH
    @GET("api/")
    Call<ImageResponse> searchImages(
            @Query("key") String key,//? auto
            @Query("q") String query//& auto
    );
}
