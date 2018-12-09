package com.thimble.customer.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thimble.customer.db.model.Image;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Dao
public interface ImageDao {

    @Query("SELECT * FROM Image")
    List<Image> getAll();

    @Query("SELECT * FROM Image WHERE customerId=:customerId AND imgType=:imgType")
    List<Image> getTypeWiseCustomerImages(String customerId, String imgType);

    @Insert
    void insert(Image image);

    @Insert
    void insert(List<Image> images);


    @Insert
    void insertAll(Image... sounds);


    @Update
     void update(Image image);


    @Delete
     void delete(Image image);
}
