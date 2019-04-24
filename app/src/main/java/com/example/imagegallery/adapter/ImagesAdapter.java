package com.example.imagegallery.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.imagegallery.R;
import com.example.imagegallery.model.Images;
import com.example.imagegallery.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Images> mImages;
    private OnImageListener mOnImageListener;

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int EXHAUSTED_TYPE = 4;

    public ImagesAdapter(OnImageListener mOnRecipeListener) {
        this.mOnImageListener = mOnRecipeListener;
        this.mImages = mImages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        switch (i) {
            case RECIPE_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_image_list, viewGroup, false);
                return new ImageViewHolder(view);
            }

            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading, viewGroup, false);
                return new GenericViewHolder(view);
            }

            case CATEGORY_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list, viewGroup, false);
                return new CategoryViewHolder(view, mOnImageListener);
            }

            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_image_list, viewGroup, false);
                return new ImageViewHolder(view);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == RECIPE_TYPE)
        {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(mImages.get(i).getImageURL())
                    .into(((ImageViewHolder) viewHolder).image);

            ((ImageViewHolder) viewHolder).title.setText(mImages.get(i).gettags());
            ((ImageViewHolder) viewHolder).ImageCreator.setText(mImages.get(i).getUser());
            ((ImageViewHolder) viewHolder).socialScore.setText(String.valueOf(mImages.get(i).getLikes()));
        }
        else if(itemViewType == CATEGORY_TYPE){

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.example.imagegallery/drawable/" + mImages.get(i).getImageURL());
            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder)viewHolder).categoryImage);

            ((CategoryViewHolder)viewHolder).categoryTitle.setText(mImages.get(i).gettags());

        }
    }

    @Override
    public int getItemViewType(int position) {

        if(mImages.get(position).getLikes() == -1){
            return CATEGORY_TYPE;
        }
       else if (mImages.get(position).gettags().equals("LOADING...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }

    }

    public void displayLoading() {
        if (!isLoading()) {
            Images recipe = new Images();
            recipe.settags("LOADING...");
            List<Images> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mImages = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (mImages != null) {
            if (mImages.size() > 0) {
                if (mImages.get(mImages.size() - 1).gettags().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void displaySearchCategories(){
        List<Images> categories = new ArrayList<>();
        for(int i = 0; i< Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Images images = new Images();
            images.settags(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            images.setImageURL(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            images.setLikes(-1);
            categories.add(images);
        }
        mImages = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mImages != null) {
            return mImages.size();
        }
        return 0;
    }

    public void setImages(List<Images> images) {
        mImages = images;
        notifyDataSetChanged();
    }
}