package com.example.imagegallery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.imagegallery.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    TextView title, ImageCreator, socialScore;
    AppCompatImageView image;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);


        title = itemView.findViewById(R.id.image_title);
        ImageCreator = itemView.findViewById(R.id.image_creator);
        socialScore = itemView.findViewById(R.id.like);
        image = itemView.findViewById(R.id.image_view);

    }
}