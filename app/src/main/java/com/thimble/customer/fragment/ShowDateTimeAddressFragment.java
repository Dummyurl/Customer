package com.thimble.customer.fragment;

import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thimble.customer.R;
import com.thimble.customer.databinding.FragmentDateTimeAddressBinding;

import java.util.Calendar;


public class ShowDateTimeAddressFragment extends Fragment {


    private FragmentDateTimeAddressBinding binding;


    public ShowDateTimeAddressFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date_time_address, container, false);

        binding.tvTimeFrom.setOnClickListener(this::onClick);
        binding.tvTimeTo.setOnClickListener(this::onClick);

        return binding.getRoot();
    }



    public void onClick(View view ){
        switch (view.getId()){
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (view, hourOfDay, minute) ->{
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



