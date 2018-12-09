package com.thimble.customer.db.model;

import android.arch.persistence.room.Ignore;

/**
 * Created by pasari on 29/11/18.
 */

public class DateHours {

    private String date;
    private String fromTime;
    private String toTime;

    public DateHours() {

    }

    @Ignore
    public DateHours(String date, String fromTime, String toTime) {
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
}
