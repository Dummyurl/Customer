package com.thimble.customer.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thimble.customer.R;
import com.thimble.customer.base.AppClass;
import com.thimble.customer.databinding.ActivityLoginBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.States;
import com.thimble.customer.model.LoginData;
import com.thimble.customer.model.LoginResponse;
import com.thimble.customer.model.StatesResponse;
import com.thimble.customer.rest.ApiClient;
import com.thimble.customer.rest.ApiHelper;
import com.thimble.customer.rest.ApiInterface;
import com.thimble.customer.view.CustomLoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }


    public void onClick(View view ){
        switch (view.getId()){
            case R.id.tvSignIn:
                if(validate()){
                    login();
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                break;
            case R.id.tvForgotPWD:
                break;
        }
    }



    private boolean validate(){
        if(TextUtils.isEmpty(binding.etUserId.getText().toString().trim())){
            Toast.makeText(this, R.string.user_id_blank_msg, Toast.LENGTH_SHORT).show();
            return false;

        } else if(TextUtils.isEmpty(binding.etPassword.getText().toString().trim())){
            Toast.makeText(this, R.string.pwd_blank_msg, Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


    class SaveTask extends AsyncTask<Void, Void, Void> {
        private List<States> states;
        private LoginData loginData;

        public SaveTask(List<States> states,LoginData loginData) {
            this.states = states;
            this.loginData = loginData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AppClass.getInstance().setStates(states);

                DBClient.getInstance(getApplicationContext()).getAppDB()
                        .statesDao().insert(states);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            AppClass.getInstance().setUserData(loginData);

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }


    private void login() {
        CustomLoder.showCustomProgressBar(this);

        Map<String, Object> params = new HashMap<>();
        params.put(ApiHelper.USER_ID, binding.etUserId.getText().toString().trim());
        params.put(ApiHelper.PASSWORD, binding.etPassword.getText().toString().trim());


        ApiClient.getClient(this).create(ApiInterface.class).
                login(params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                System.out.println("response:"+response);
                CustomLoder.dismisCustomProgressBar();
                try {
                    String responseBody=response.body().string();
                    System.out.println("responseBody:"+responseBody);

                    if(response.code() == 200){
                        LoginResponse login = new Gson().fromJson(responseBody,LoginResponse.class);
                        if(login.getPayload().get(0).getAuthorization().toLowerCase().equals("success")){

//                            AppClass.getInstance().setUserData(login.getPayload().get(0));
//
//                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                            finish();

                            loadStates(login.getPayload().get(0));
                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
                CustomLoder.dismisCustomProgressBar();
            }
        });
    }

    private void loadStates(LoginData loginData) {
        CustomLoder.showCustomProgressBar(this);

        ApiClient.getClient(this).create(ApiInterface.class)
                .loadStates(loginData.getTokenType() + " " + loginData.getAccessToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                System.out.println("response:"+response);
                CustomLoder.dismisCustomProgressBar();
                try {
                    String responseBody = response.body().string();
                    System.out.println("responseBody:"+responseBody);

                    if(response.code() == 200){
                        StatesResponse result = new Gson().fromJson(responseBody,StatesResponse.class);

                        new SaveTask(result.getPayload(),loginData).execute();

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
                CustomLoder.dismisCustomProgressBar();
            }
        });
    }
}
