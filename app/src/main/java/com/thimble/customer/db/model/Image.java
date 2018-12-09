package com.thimble.customer.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by pasari on 28/11/18.
 */


@Entity(tableName = "Image", indices = {@Index(value = {"customerId"})},
        foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "id",
        childColumns = "customerId", onDelete = CASCADE,onUpdate = CASCADE))

public class Image {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("CustomerID")
    @Expose
    private String customerId;
    private String imgType;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
    private int synced;

    @Ignore
    private Bitmap imgBitmap;

    @Ignore
    private String imgUrl;

    @SerializedName("CompanyID")
    @Expose
    @Ignore
    private String companyID;
    @SerializedName("UploadShopImagePath")
    @Expose
    private String imgPath;
    @SerializedName("UploadShopImageName")
    @Expose
    private String imgName;


    public Image() {
    }


    @Ignore
    public Image(String imgType, Bitmap imgBitmap) {
        this.imgType = imgType;
        this.imgBitmap = imgBitmap;
    }

    @Ignore
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
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

    public int getSynced() {
        return synced;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
