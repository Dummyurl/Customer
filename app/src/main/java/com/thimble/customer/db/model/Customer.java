package com.thimble.customer.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.thimble.customer.db.DateTimeTypeConverter;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Entity
@TypeConverters(DateTimeTypeConverter.class)
public class Customer {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    private String userLocalId;
    private String userServerId;

    private String customerName;
    private String address;
    private String phNo;
    private String emailId;
    private String webLink;
    private String storeAddress;
    private String storeLat;
    private String storeLng;
    private String rcvAddress;
    private String rcvLat;
    private String rcvLng;
    private int synced;

    private List<DateTime> dateTime;

    @Ignore
    private boolean isSelected;



//    @Embedded
//    private DateTime dateTime;


    public Customer() {
    }

    public Customer(String userName) {
        this.customerName = userName;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserLocalId() {
        return userLocalId;
    }

    public void setUserLocalId(String userLocalId) {
        this.userLocalId = userLocalId;
    }

    public String getUserServerId() {
        return userServerId;
    }

    public void setUserServerId(String userServerId) {
        this.userServerId = userServerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(String storeLat) {
        this.storeLat = storeLat;
    }

    public String getStoreLng() {
        return storeLng;
    }

    public void setStoreLng(String storeLng) {
        this.storeLng = storeLng;
    }

    public String getRcvAddress() {
        return rcvAddress;
    }

    public void setRcvAddress(String rcvAddress) {
        this.rcvAddress = rcvAddress;
    }

    public String getRcvLat() {
        return rcvLat;
    }

    public void setRcvLat(String rcvLat) {
        this.rcvLat = rcvLat;
    }

    public String getRcvLng() {
        return rcvLng;
    }

    public void setRcvLng(String rcvLng) {
        this.rcvLng = rcvLng;
    }

    public int getSynced() {
        return synced;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }

    //    public DateTime getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(DateTime dateTime) {
//        this.dateTime = dateTime;
//    }

    public List<DateTime> getDateTime() {
        return dateTime;
    }

    public void setDateTime(List<DateTime> dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
