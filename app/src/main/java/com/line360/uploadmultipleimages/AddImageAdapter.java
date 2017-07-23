package com.line360.uploadmultipleimages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 17/05/2017.
 */

public class AddImageAdapter extends RecyclerView.Adapter<AddImageViewHolder> {
    private ArrayList<Image> arrayList;
    private Context context;

    public AddImageAdapter(ArrayList<Image> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public AddImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.image_frame, parent, false);
        AddImageViewHolder listHolder = new AddImageViewHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(AddImageViewHolder holder, int position) {

        final Image model = arrayList.get(position);

        AddImageViewHolder mainHolder = (AddImageViewHolder) holder;// holder


        //---------------------------------------------------------------------------------------------------------

        mainHolder.imageview.setImageURI(model.getImageResource());

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }
}
