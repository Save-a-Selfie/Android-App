package com.imsearch.models;

import android.graphics.Bitmap;

/**
 * Created by rushd on 29/09/2016.
 */

public class CreateList {

    private String image_title;
    private Integer image_id;
    private Bitmap bitmap;

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String android_version_name) {
        this.image_title = android_version_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }

    public void setImage_ID(Integer android_image_url) {
        this.image_id = android_image_url;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}