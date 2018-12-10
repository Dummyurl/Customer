package com.thimble.customer.base;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.thimble.customer.db.model.States;
import com.thimble.customer.model.LoginData;

import java.util.List;

/**
 * Created by pasari on 25/11/18.
 */

public class AppClass extends Application {


    private static AppClass instance;

    private LoginData userData;
    private boolean isLogin;
    private List<States> states;
    private boolean changed;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        loadUserData();
    }

    public static AppClass getInstance() {
        return instance;
    }


    public boolean isLogin() {
        return getSharedPreferences(AppConstant.PREFS_NAME,Context.MODE_PRIVATE)
                .getBoolean(AppConstant.IS_LOGIN,false);
    }

    public void setLogin(boolean login) {
        getSharedPreferences(AppConstant.PREFS_NAME,Context.MODE_PRIVATE)
                .edit().putBoolean(AppConstant.IS_LOGIN,login).commit();
        this.isLogin = login;
    }

    public LoginData getUserData() {
        return userData;
    }

    public void setUserData(LoginData userData) {
        this.userData = userData;

        getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(AppConstant.LOGIN_PREF,
                new Gson().toJson(userData)).commit();

        setLogin(true);
    }

    public String getAccessToken(){
        return (userData.getTokenType() == null ? "" : userData.getTokenType()) + " " +
                (userData.getAccessToken() == null ? "" : userData.getAccessToken());
    }

    private void loadUserData(){
        if(getSharedPreferences(AppConstant.PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(AppConstant.IS_LOGIN,false)){

            String s = getSharedPreferences(AppConstant.PREFS_NAME,Context.MODE_PRIVATE)
                    .getString(AppConstant.LOGIN_PREF,"");

            this.userData = new Gson().fromJson(s,LoginData.class);
        }
    }


    public List<States> getStates() {
        return states;
    }

    public void setStates(List<States> states) {
        this.states = states;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
