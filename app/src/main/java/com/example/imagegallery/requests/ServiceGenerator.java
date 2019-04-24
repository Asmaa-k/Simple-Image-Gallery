package com.example.imagegallery.requests;

import com.example.imagegallery.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ImageApi imageApi = retrofit.create(ImageApi.class);

    public static ImageApi getImageApi(){
        return imageApi;
    }
}
