package com.thimble.customer.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thimble.customer.db.model.DateTime;

import java.lang.reflect.Type;
import java.util.List;

public class AnswerTypeConverter {

    @TypeConverter
    public static List<DateTime> stringToQuestionModels(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DateTime>>() {}.getType();
        List<DateTime> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String questionToString(List<DateTime> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DateTime>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
