package com.thimble.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.Image;

import java.util.List;

public class ListResponse {


    @SerializedName("Table1")
    @Expose
    private List<com.thimble.customer.db.model.Customer> customers = null;
    @SerializedName("SectionImg")
    @Expose
    private List<Image> sectionImg = null;
    @SerializedName("ShopOutsideImg")
    @Expose
    private List<Image> shopOutsideImg = null;
    @SerializedName("ShopInsideImg")
    @Expose
    private List<Image> shopInsideImg = null;
    @SerializedName("DayHours")
    @Expose
    private List<DateHours> dayHours = null;


    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Image> getSectionImg() {
        return sectionImg;
    }

    public void setSectionImg(List<Image> sectionImg) {
        this.sectionImg = sectionImg;
    }

    public List<Image> getShopOutsideImg() {
        return shopOutsideImg;
    }

    public void setShopOutsideImg(List<Image> shopOutsideImg) {
        this.shopOutsideImg = shopOutsideImg;
    }

    public List<Image> getShopInsideImg() {
        return shopInsideImg;
    }

    public void setShopInsideImg(List<Image> shopInsideImg) {
        this.shopInsideImg = shopInsideImg;
    }

    public List<DateHours> getDayHours() {
        return dayHours;
    }

    public void setDayHours(List<DateHours> dayHours) {
        this.dayHours = dayHours;
    }
}
