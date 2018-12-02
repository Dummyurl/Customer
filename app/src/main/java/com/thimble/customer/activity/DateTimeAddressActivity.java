package com.thimble.customer.activity;

import android.content.Intent;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_time_address);


        init();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    public void onClick(View view ){
        switch (view.getId()){
            case R.id.tvEdit:
                startActivity(new Intent(this,EditDateTimeAddressActivity.class));
                break;
            case R.id.tvDelete:
                break;
        }
    }




}
