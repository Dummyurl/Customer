package com.thimble.customer.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by pasari on 28/11/18.
 */


@Entity
//@TypeConverters(AnswerTypeConverter.class)
public class Customer {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    private String userLocalId;
    private String userServerId;

    private String userName;
    private String address;
    private String phNo;
    private String emailId;
    private String webLink;
    private String locAddress;
    private String locLattitude;
    private String locLongitude;

    @Ignore
    private boolean isSelected;

//    @Embedded
//    private List<DateTime> dateTime;

    @Embedded
    private DateTime dateTime;


    public Customer() {
    }

    public Customer(String userName) {
        this.userName = userName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public String getLocLattitude() {
        return locLattitude;
    }

    public void setLocLattitude(String locLattitude) {
        this.locLattitude = locLattitude;
    }

    public String getLocLongitude() {
        return locLongitude;
    }

    public void setLocLongitude(String locLongitude) {
        this.locLongitude = locLongitude;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    //    public List<DateTime> getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(List<DateTime> dateTime) {
//        this.dateTime = dateTime;
//    }
}
