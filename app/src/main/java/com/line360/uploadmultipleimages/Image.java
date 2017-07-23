package com.line360.uploadmultipleimages;

import android.net.Uri;

/**
 * Created by Ahmed Yehya on 17/05/2017.
 */

public class Image {
    Uri ImageResource;

    public Image(Uri imageResource) {
        ImageResource = imageResource;
    }

    public Uri getImageResource() {
        return ImageResource;
    }
}
