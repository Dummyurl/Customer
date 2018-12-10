package com.thimble.customer.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import com.theartofdev.edmodo.cropper.CropImage;
import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.databinding.ActivityShowCustomerBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.DateHours;
import com.thimble.customer.db.model.Image;
import com.thimble.customer.util.BitmapManager;
import com.thimble.customer.util.IntentExtras;


import java.util.ArrayList;
import java.util.List;


import static com.thimble.customer.base.AppConstant.INSIDE_PIC;
import static com.thimble.customer.base.AppConstant.OUTSIDE_PIC;
import static com.thimble.customer.base.AppConstant.SECTION_PIC;
import static com.thimble.customer.util.IntentExtras.CUSTOMER_ID;

public class ShowCustomerActivity extends AppCompatActivity {

    private ActivityShowCustomerBinding binding;

    private Customer customer;

    private List<Image> outsideList;
    private List<Image> insideList;
    private List<Image> sectionList;
    private List<DateHours> dateTimes;

    private ShowImgAdapter outsideAdapter;
    private ShowImgAdapter insideAdapter;
    private ShowImgAdapter sectionAdapter;

    private int dateTimePosition = 0;
    private String customerID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_customer);

        customerID = getIntent().getStringExtra(IntentExtras.CUSTOMER_ID);
        if (customerID == null) finish();
        else init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchCustomerFromDB();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.rgTime.setOnCheckedChangeListener(this::onCheckedChanged);

    }

    private void fetchCustomerFromDB(){
        new FetchCustomerTask().execute();
    }


    private void setUI(Customer customer,List<Image> outsideList,List<Image> insideList,List<Image> sectionList){
        if(customer != null) {
            binding.tvName.setText(customer.getName());
            binding.tvId.setText(customer.getUserID());
            binding.tvPhone.setText(customer.getContactNo());
            binding.tvEmail.setText(customer.getEmailID());
            binding.tvCity.setText(customer.getCity());
            binding.tvState.setText(customer.getState());
            binding.tvZip.setText(customer.getZip());
            binding.tvWebsite.setText(customer.getWebsite());

            binding.tvStoreAddress.setText(customer.getStoreEntranceLocation());
            binding.tvStoreLat.setText(customer.getStoreEntranceLatitude());
            binding.tvStoreLng.setText(customer.getStoreEntranceLongitude());

            binding.tvRcvAddress.setText(customer.getReceivingEntranceLocation());
            binding.tvRcvLat.setText(customer.getReceivingEntranceLatitude());
            binding.tvRcvLng.setText(customer.getReceivingEntranceLongitude());

            dateTimes = customer.getDayHours() != null ? customer.getDayHours() : prepareDateTime();
            binding.rdbSunday.setChecked(true);

            setImageLists(outsideList,insideList,sectionList);
        }else {
            finish();
        }
    }

    private void setImageLists(List<Image> outsideList,List<Image> insideList,List<Image> sectionList){
        binding.rvOutside.setAdapter(new ShowImgAdapter(this, outsideList));

        binding.rvInside.setAdapter(new ShowImgAdapter(this, insideList));

        binding.rvSection.setAdapter(new ShowImgAdapter(this, sectionList));
    }


    private List<DateHours> prepareDateTime(){
        List<DateHours> dateTimes;
        dateTimes = new ArrayList<DateHours>(){{
            add(new DateHours("SUN","",""));
            add(new DateHours("MON","",""));
            add(new DateHours("TUE","",""));
            add(new DateHours("WED","",""));
            add(new DateHours("THU","",""));
            add(new DateHours("FRI","",""));
            add(new DateHours("SAT","",""));
        }};

        return dateTimes;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvEdit:
                startActivity(new Intent(this,AddCustomerActivity.class)
                        .putExtra(CUSTOMER_ID,customerID));
                break;
            case R.id.tvDelete:
                onBackPressed();
                break;
        }
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rdbSunday:
                setDateTimes(dateTimePosition = 0);
                break;
            case R.id.rdbMonday:
                setDateTimes(dateTimePosition = 1);
                break;
            case R.id.rdbTuesday:
                setDateTimes(dateTimePosition = 2);
                break;
            case R.id.rdbWednesday:
                setDateTimes(dateTimePosition = 3);
//                setDateTimes(3);
                break;
            case R.id.rdbThursday:
                setDateTimes(dateTimePosition = 4);
//                setDateTimes(4);
                break;
            case R.id.rdbFriday:
                setDateTimes(dateTimePosition = 5);
//                setDateTimes(5);
                break;
            case R.id.rdbSaturday:
                setDateTimes(dateTimePosition = 6);
//                setDateTimes(6);
                break;
        }
    }

    private void setDateTimes(int position){
        {
            String time = dateTimes.get(position).getFromTime();
            time = (time == null || time.equals("") ? "-- : --" : time);
            binding.tvTimeFrom.setText(time);
        }

        {
            String time = dateTimes.get(position).getToTime();
            time = (time == null || time.equals("") ? "-- : --" : time);
            binding.tvTimeTo.setText(time);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);
//                    startCropImageActivity(imageUri);
                }
                break;
        }
    }



    class FetchCustomerTask extends AsyncTask<Void, Void, Void> {
        private Customer customer;
        private List<Image> outsideList;
        private List<Image> insideList;
        private List<Image> sectionList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

             customer = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .customerDao()
                    .getCustomerDetails(customerID);

            outsideList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .imageDao()
                    .getTypeWiseCustomerImages(customerID,OUTSIDE_PIC);

            insideList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .imageDao()
                    .getTypeWiseCustomerImages(customerID,INSIDE_PIC);

            sectionList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .imageDao()
                    .getTypeWiseCustomerImages(customerID,SECTION_PIC);

            for(Image image : outsideList){
                image.setImgBitmap(image.getImage() == null ? null : BitmapManager.byteToBitmap(image.getImage()));
            }

            for(Image image : insideList){
                image.setImgBitmap(image.getImage() == null ? null : BitmapManager.byteToBitmap(image.getImage()));
            }

            for(Image image : sectionList){
                image.setImgBitmap(image.getImage() == null ? null : BitmapManager.byteToBitmap(image.getImage()));
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            setUI(customer,outsideList,insideList,sectionList);
        }
    }
}
