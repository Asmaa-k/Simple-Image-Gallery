package com.example.imagegallery.requests;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.imagegallery.model.ImageResponse;
import com.example.imagegallery.model.Images;
import com.example.imagegallery.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.imagegallery.util.Constants.NETWORK_TIMEOUT;

public class ImageApiClient {
    private static final String TAG = "ImageApiClient";
    private static ImageApiClient instance;
    private MutableLiveData<List<Images>> mImages;
    private RetrieveImagesRunnable mRetrieveImagesRunnable;

    public ImageApiClient() {
        mImages = new MutableLiveData<>();
    }

    public static ImageApiClient getInstance() {
        if (instance == null)
            instance = new ImageApiClient();
        return instance;
    }

    public MutableLiveData<List<Images>> getmImages() {
        return mImages;
    }

    public void searchImagesApi(String query) {
        if (mRetrieveImagesRunnable != null) {
            mRetrieveImagesRunnable = null;
        }
        mRetrieveImagesRunnable = new RetrieveImagesRunnable(query);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveImagesRunnable);

        // Set a timeout for the data refresh
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know it timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveImagesRunnable implements Runnable {

        private String query;
        private boolean cancelRequest;

        private RetrieveImagesRunnable(String query) {
            this.query = query;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getImages(query).execute();
                if (cancelRequest) return;//to stop the run
                if (response.code() == 200) {
                    List<Images> list = new ArrayList<>(((ImageResponse) response.body()).getImages());
                    mImages.postValue(list);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: error: " + error);
                    mImages.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mImages.postValue(null);
            }
        }

        private Call<ImageResponse> getImages(String query) {
            return ServiceGenerator.getImageApi().searchImages(Constants.API_KEY, query);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the retrieval query");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveImagesRunnable != null) {
            mRetrieveImagesRunnable.cancelRequest();
        }
    }
}

