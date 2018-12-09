package com.thimble.customer.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thimble.customer.db.model.DateHours;

import java.lang.reflect.Type;
import java.util.List;

public class DateTimeTypeConverter {

    @TypeConverter
    public static List<DateHours> stringToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DateHours>>() {}.getType();
        List<DateHours> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String listToString(List<DateHours> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DateHours>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
