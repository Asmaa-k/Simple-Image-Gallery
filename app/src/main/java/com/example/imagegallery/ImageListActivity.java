package com.example.imagegallery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.example.imagegallery.adapter.ImagesAdapter;
import com.example.imagegallery.adapter.OnImageListener;
import com.example.imagegallery.model.ImageResponse;
import com.example.imagegallery.model.Images;
import com.example.imagegallery.requests.ImageApi;
import com.example.imagegallery.requests.ServiceGenerator;
import com.example.imagegallery.util.Constants;
import com.example.imagegallery.util.VerticalSpacingItemDecorator;
import com.example.imagegallery.viewmodel.ImagesListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListActivity extends AppCompatActivity {
    private static final String TAG = "ImageListActivity";
    ImagesListViewModel mViewModel;

    ImagesAdapter adapter;
    RecyclerView recyclerView;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        mSearchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.images_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mViewModel = ViewModelProviders.of(this).get(ImagesListViewModel.class);
        // searchImagesApi("Cats");
        subscribeObservers();
        initRecycleView();
        initSearchView();

        if (!mViewModel.IsViewingImages()) {
            displaySearchCategories();
        }
    }

    private void initSearchView() {

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.displayLoading();
                mViewModel.searchImagesApi(s);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void subscribeObservers() {
        mViewModel.getImages().observe(this, new Observer<List<Images>>() {
            @Override
            public void onChanged(@Nullable List<Images> images) {
                if (images != null)
                    adapter.setImages(images);
            }
        });
    }

    private void initRecycleView() {
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        adapter = new ImagesAdapter(new OnImageListener() {
            @Override
            public void onCategoryClick(String category) {
                adapter.displayLoading();
                mViewModel.searchImagesApi(category);
                mSearchView.clearFocus();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void displaySearchCategories() {
        mViewModel.setViewingImages(false);
        adapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if(mViewModel.onBackPressed()){
            super.onBackPressed();
        }
        else{
            displaySearchCategories();
        }
    }

}
 /*test();
    private void test() {
        ImageApi api = ServiceGenerator.getImageApi();
        Call<ImageResponse>  call = api.searchImages(Constants.API_KEY,"cats");
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                List<Images> images = new ArrayList<>(response.body().getImages());
              for(Images image:images) Log.d(TAG, "onResponse: "+image.gettags()); }
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) { }});}
            */