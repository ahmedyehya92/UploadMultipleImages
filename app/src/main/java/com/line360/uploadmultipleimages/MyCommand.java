package com.line360.uploadmultipleimages;

import android.content.Context;

import com.android.volley.Request;

import java.util.ArrayList;

/**
 * Created by Ahmed Yehya on 19/05/2017.
 */

public class MyCommand<T> {

    private Context context;
    private ArrayList<Request<T>> requestArrayList = new ArrayList<>();

    public MyCommand(Context context)
    {
        this.context = context;
    }

    public void add(Request<T> request) {
        requestArrayList.add(request);
    }

    public void remove (Request<T> request) {
        requestArrayList.remove(request);
    }

    public void excute () {

        for (Request<T> request : requestArrayList)
        {
            MySingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}
