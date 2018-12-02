package com.thimble.customer.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.thimble.customer.db.AnswerTypeConverter;

import java.util.List;

/**
 * Created by pasari on 28/11/18.
 */


@Entity
//@TypeConverters(AnswerTypeConverter.class)
public class Customer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    private int userServerId;

    private String userName;
    private String address;
    private String phNo;
    private String emailId;
    private String webLink;
    private String locAddress;
    private String locLattitude;
    private String locLongitude;

    @Ignore
    private boolean isSelect;

//    @Embedded
//    private List<DateTime> dateTime;

    @Embedded
    private DateTime dateTime;


    public Customer() {
    }

    public Customer(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserServerId() {
        return userServerId;
    }

    public void setUserServerId(int userServerId) {
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    //    public List<DateTime> getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(List<DateTime> dateTime) {
//        this.dateTime = dateTime;
//    }
}
