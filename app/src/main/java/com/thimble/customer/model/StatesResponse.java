package com.thimble.customer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thimble.customer.db.model.States;

import java.util.List;

public class StatesResponse {

    @SerializedName("Domain")
    @Expose
    private String domain;
    @SerializedName("Event")
    @Expose
    private String event;
    @SerializedName("ClientData")
    @Expose
    private String clientData;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Payload")
    @Expose
    private List<States> payload = null;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<States> getPayload() {
        return payload;
    }

    public void setPayload(List<States> payload) {
        this.payload = payload;
    }

}
