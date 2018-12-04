package com.thimble.customer.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thimble.customer.db.model.Customer;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer")
    List<Customer> getAll();

    @Query("SELECT id,customerName FROM Customer")
    List<Customer> getAllCustomers();

    @Query("SELECT * FROM Customer WHERE id=:id")
    Customer getCustomerDetails(String id);

    @Insert
    void insert(Customer customer);


    @Update
     void update(Customer customer);


    @Delete
     void deleteCustomer(Customer customer);
}
