package com.line360.uploadmultipleimages;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Ahmed Yehya on 17/05/2017.
 */

public class AddImageViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView imageview;


    public AddImageViewHolder(View itemView) {
        super(itemView);

        this.imageview = (AppCompatImageView) itemView.findViewById(R.id.add_image);
    }
}
