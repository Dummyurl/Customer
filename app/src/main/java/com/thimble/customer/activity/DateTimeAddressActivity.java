package com.thimble.customer.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ActivityDateTimeAddressBinding;


public class DateTimeAddressActivity extends AppCompatActivity {


    private ActivityDateTimeAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_date_time_address);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_time_address);

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
