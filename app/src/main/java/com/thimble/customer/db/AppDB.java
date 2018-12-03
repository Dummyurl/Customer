package com.thimble.customer.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.thimble.customer.db.dao.CustomerDao;
import com.thimble.customer.db.dao.ImageDao;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.Image;

/**
 * Created by pasari on 28/11/18.
 */

@Database(entities = {Customer.class, Image.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    public abstract CustomerDao customerDao();
    public abstract ImageDao imageDao();
}