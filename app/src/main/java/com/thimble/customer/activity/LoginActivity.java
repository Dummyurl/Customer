package com.thimble.customer.activity;

//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thimble.customer.R;
//import com.thimble.customer.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }


    public void onClick(View view ){
        switch (view.getId()){
            case R.id.tvSignIn:
                break;
            case R.id.tvForgotPWD:
                break;
        }
    }
}
