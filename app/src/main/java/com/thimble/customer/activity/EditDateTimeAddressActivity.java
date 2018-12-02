package com.thimble.customer.activity;

import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ActivityDateTimeAddressBinding;
import com.thimble.customer.databinding.ActivityEditDateTimeAddressBinding;

import java.util.Calendar;


public class EditDateTimeAddressActivity extends AppCompatActivity {


   private ActivityEditDateTimeAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_date_time_address);


        init();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }

    public void onClick(View view ){
        switch (view.getId()){
            case R.id.tvSave:
                break;
            case R.id.tvCancel:
                break;
            case R.id.tvTimeFrom:
                pickTime(binding.tvTimeFrom);
                break;
            case R.id.tvTimeTo:
                pickTime(binding.tvTimeTo);
                break;
        }
    }


    private void pickTime(TextView textView){
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) ->{
            showTime(textView,hourOfDay,minute); }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void showTime(TextView textView,int hourOfDay, int minute) {
        String format = "";
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        textView.setText(new StringBuilder().append(String.format("%02d", hourOfDay)).append(" : ").append(minute)
                .append(" ").append(format));
    }

}