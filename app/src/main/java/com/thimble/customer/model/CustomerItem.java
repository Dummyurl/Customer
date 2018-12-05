package com.thimble.customer.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerItem {

//    private String id;
//    private String customerName;

    @SerializedName("USERID")
    @Expose
    private String id;
    @SerializedName("NAME")
    @Expose
    private String customerName;

    @Ignore
    @SerializedName("CONTACTNO")
    @Expose
    private String phNo;

    @Ignore
    @SerializedName("ADDRESS")
    @Expose
    private String address;

    @Ignore
    @SerializedName("STREET")
    @Expose
    private String street;

    @Ignore
    @SerializedName("CITY")
    @Expose
    private String city;

    @Ignore
    @SerializedName("STATE")
    @Expose
    private String state;

    @Ignore
    @SerializedName("ZIP")
    @Expose
    private String zip;

    @Ignore
    @SerializedName("COMPANYID")
    @Expose
    private String companyId;

    @Ignore
    @SerializedName("COMPANYNAME")
    @Expose
    private String companyName;



    @Ignore
    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
