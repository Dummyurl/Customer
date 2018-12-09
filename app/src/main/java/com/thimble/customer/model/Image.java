package com.thimble.customer.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by pasari on 28/11/18.
 */


public class Image {

    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("CompanyID")
    @Expose
    private String companyID;
    @SerializedName("SectionID")
    @Expose
    private String sectionID;
    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("SectionImagePath")
    @Expose
    private String sectionImagePath;
    @SerializedName("SectionImageName")
    @Expose
    private String sectionImageName;


    public Image() {
    }


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionImagePath() {
        return sectionImagePath;
    }

    public void setSectionImagePath(String sectionImagePath) {
        this.sectionImagePath = sectionImagePath;
    }

    public String getSectionImageName() {
        return sectionImageName;
    }

    public void setSectionImageName(String sectionImageName) {
        this.sectionImageName = sectionImageName;
    }
}
