package com.thimble.customer.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.thimble.customer.util.BitmapManager;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by pasari on 28/11/18.
 */


@Entity(foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "id",
        childColumns = "userId", onDelete = CASCADE,onUpdate = CASCADE))

public class Image {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String imgType;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @Ignore
    private Bitmap imgBitmap;

    @Ignore
    private Uri imgUri;
    @Ignore
    private String imgUrl;

    public Image() {
    }

    public Image(String imgType, Uri imgUri) {
        this.imgType = imgType;
        this.imgUri = imgUri;
    }

    public Image(String imgType, Bitmap imgBitmap) {
        this.imgType = imgType;
        this.imgBitmap = imgBitmap;
    }

    public Image(String imgType, byte[] image, Bitmap imgBitmap) {
        this.imgType = imgType;
        this.image = image;
        this.imgBitmap = imgBitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }
}
