package com.thimble.customer.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thimble.customer.db.model.Image;
import com.thimble.customer.db.model.States;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Dao
public interface StatesDao {

    @Query("SELECT * FROM Image")
    List<States> getAll();

    @Insert
    void insert(States states);

    @Insert
    void insert(List<States> states);

    @Insert
    void insertAll(Image... sounds);


    @Update
     void update(Image image);


    @Delete
     void delete(Image image);
}
