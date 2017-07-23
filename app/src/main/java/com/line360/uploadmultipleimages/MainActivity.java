package com.line360.uploadmultipleimages;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GalleryPhoto galleryPhoto;
    ArrayList<String> imageList = new ArrayList<>();
    final int GALLERY_REQUEST = 1200;
    private static RecyclerView addImRecyclerView;
    public static ArrayList<Image> mImages;
    AddImageAdapter imAdapter;
    Button selectImage,uploadImage;

    final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean result = isReadExternalStoragePermissionGranted();
        initViews();
        mImages = new ArrayList<Image>();
        imAdapter = new AddImageAdapter(mImages,MainActivity.this);
        addImRecyclerView.setAdapter(imAdapter);
        imAdapter.notifyDataSetChanged();

        selectImage = (Button)findViewById(R.id.btn_add);
        uploadImage = (Button) findViewById(R.id.upload_image) ;
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = galleryPhoto.openGalleryIntent();
                startActivityForResult(in, GALLERY_REQUEST);
            }
        });

        final MyCommand myCommand = new MyCommand(getApplicationContext());

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String imagePath : imageList)
                {
                    try {
                        Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(600,600).getBitmap();

                        final String encodedString = ImageBase64.encode(bitmap);


                        String url = "http://telegraphic-miscond.000webhostapp.com/halls_manager/v1/upimages";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("hallid","3");
                                params.put("image", encodedString);


                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();

                                headers.put("Authorization", "6311ad303626f38644e024c81d520e82");
                                return headers;
                            }
                        };
                        myCommand.add(stringRequest);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }


                myCommand.excute();

            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    String realPath = galleryPhoto.getPath();

                    imageList.add(realPath);
                    System.out.println(realPath);

                    // Set the image in ImageView

                    mImages.add(new Image(selectedImageUri) );
                    imAdapter.notifyDataSetChanged();
                }
            }
        }
    }





    private void initViews() {

        addImRecyclerView = (RecyclerView)
                findViewById(R.id.images_recycler_view);
        addImRecyclerView.setHasFixedSize(true);
        addImRecyclerView
                .setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

    }

    public  boolean isReadExternalStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
