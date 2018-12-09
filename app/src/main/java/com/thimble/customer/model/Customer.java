package com.thimble.customer.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thimble.customer.db.model.DateHours;

import java.util.List;

public class Customer {

    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Zip")
    @Expose
    private String zip;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("Website")
    @Expose
    private String website;
    @SerializedName("SalesManID")
    @Expose
    private String salesManID;
    @SerializedName("WHCompany")
    @Expose
    private String wHCompany;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("StoreEntranceLocation")
    @Expose
    private String storeEntranceLocation;
    @SerializedName("StoreEntranceLatitude")
    @Expose
    private String storeEntranceLatitude;
    @SerializedName("StoreEntranceLongitude")
    @Expose
    private String storeEntranceLongitude;
    @SerializedName("ReceivingEntranceLocation")
    @Expose
    private String receivingEntranceLocation;
    @SerializedName("ReceivingEntranceLatitude")
    @Expose
    private String receivingEntranceLatitude;
    @SerializedName("ReceivingEntranceLongitude")
    @Expose
    private String receivingEntranceLongitude;
    private int synced;

    private List<DateHours> dateTime;

    @Ignore
    private boolean isSelected;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSalesManID() {
        return salesManID;
    }

    public void setSalesManID(String salesManID) {
        this.salesManID = salesManID;
    }

    public String getwHCompany() {
        return wHCompany;
    }

    public void setwHCompany(String wHCompany) {
        this.wHCompany = wHCompany;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStoreEntranceLocation() {
        return storeEntranceLocation;
    }

    public void setStoreEntranceLocation(String storeEntranceLocation) {
        this.storeEntranceLocation = storeEntranceLocation;
    }

    public String getStoreEntranceLatitude() {
        return storeEntranceLatitude;
    }

    public void setStoreEntranceLatitude(String storeEntranceLatitude) {
        this.storeEntranceLatitude = storeEntranceLatitude;
    }

    public String getStoreEntranceLongitude() {
        return storeEntranceLongitude;
    }

    public void setStoreEntranceLongitude(String storeEntranceLongitude) {
        this.storeEntranceLongitude = storeEntranceLongitude;
    }

    public String getReceivingEntranceLocation() {
        return receivingEntranceLocation;
    }

    public void setReceivingEntranceLocation(String receivingEntranceLocation) {
        this.receivingEntranceLocation = receivingEntranceLocation;
    }

    public String getReceivingEntranceLatitude() {
        return receivingEntranceLatitude;
    }

    public void setReceivingEntranceLatitude(String receivingEntranceLatitude) {
        this.receivingEntranceLatitude = receivingEntranceLatitude;
    }

    public String getReceivingEntranceLongitude() {
        return receivingEntranceLongitude;
    }

    public void setReceivingEntranceLongitude(String receivingEntranceLongitude) {
        this.receivingEntranceLongitude = receivingEntranceLongitude;
    }

    public List<DateHours> getDateTime() {
        return dateTime;
    }

    public void setDateTime(List<DateHours> dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
