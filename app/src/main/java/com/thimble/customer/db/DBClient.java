package com.thimble.customer.db;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by pasari on 28/11/18.
 */

public class DBClient {


    private Context mContext;
    private static DBClient mInstance;

    private AppDB appDB;

    private DBClient(Context mContext) {
        this.mContext = mContext;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDB = Room.databaseBuilder(mContext, AppDB.class, "CUSTOMER_DB").build();
    }

    public static synchronized DBClient getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new DBClient(mContext);
        }
        return mInstance;
    }

    public AppDB getAppDB() {
        return appDB;
    }


}
