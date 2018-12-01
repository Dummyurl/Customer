package com.thimble.customer.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import com.thimble.customer.db.model.Customer;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by pasari on 28/11/18.
 */


public class Image {

    private int id;
    private int userId;
    private String imgType;
    private Uri imgUri;
    private String imgUrl;


    public Image() {
    }


    public Image(String imgType) {
        this.imgType = imgType;
    }

    public Image(String imgType, Uri imgUri) {
        this.imgType = imgType;
        this.imgUri = imgUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
