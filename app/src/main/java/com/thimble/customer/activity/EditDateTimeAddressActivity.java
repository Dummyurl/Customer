package com.thimble.customer.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ActivityDateTimeAddressBinding;
import com.thimble.customer.databinding.ActivityEditDateTimeAddressBinding;
import com.thimble.customer.db.DBClient;

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
                startActivity(new Intent(this,LocationActivity.class));
                break;
            case R.id.tvCancel:
                onBackPressed();
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


    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

//            Customer customer = new Customer();
//            customer.setAddress("setAddress");
//            customer.setEmailId("setAddress");
//            customer.setLocAddress("setLocAddress");
//            customer.setCustomerName("setAddress");
//
//            DateTime dateTime = new DateTime();
//            dateTime.setDate("nbjv,mnxc,mvn");
//
//            customer.setDateTime(dateTime);

            //adding to database
//            DBClient.getInstance(getApplicationContext()).getAppDB()
//                    .customerDao().insert(customer);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();


//            new GetTasks().execute();
        }
    }

}
