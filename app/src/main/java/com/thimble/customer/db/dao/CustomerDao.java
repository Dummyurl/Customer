package com.thimble.customer.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thimble.customer.db.model.Customer;
import com.thimble.customer.model.CustomerItem;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer")
    List<Customer> getAll();

    @Query("SELECT id,name,synced FROM Customer WHERE synced =:synced")
    List<CustomerItem> getAllCustomers(int synced);

    @Query("SELECT * FROM Customer WHERE id=:id Limit 1")
    Customer getCustomerDetails(String id);

    @Insert
    void insert(Customer customer);

    @Insert
    void insert(List<Customer> customers);

    @Update
     void update(Customer customer);

    @Delete
     void deleteCustomer(Customer customer);

    @Delete
     void deleteCustomer(List<Customer> customers);

    @Query("DELETE FROM Customer WHERE id = :id")
     void deleteCustomers(String id);
}
