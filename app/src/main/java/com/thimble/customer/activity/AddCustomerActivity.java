package com.thimble.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.databinding.ActivityAddCustomerBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.DateTime;
import com.thimble.customer.db.model.Image;
import com.thimble.customer.model.LocationResponse;
import com.thimble.customer.rest.ApiClient;
import com.thimble.customer.rest.ApiInterface;
import com.thimble.customer.util.BitmapManager;
import com.thimble.customer.util.FileUtils;
import com.thimble.customer.util.ImageCompresseor;
import com.thimble.customer.util.IntentExtras;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thimble.customer.base.AppConstant.INSIDE_PIC;
import static com.thimble.customer.base.AppConstant.OUTSIDE_PIC;
import static com.thimble.customer.base.AppConstant.REQUEST_CHECK_SETTINGS_GPS;
import static com.thimble.customer.base.AppConstant.SECTION_PIC;

public class AddCustomerActivity extends AppCompatActivity implements
        ShowImgAdapter.onItemClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private ActivityAddCustomerBinding binding;

    private GoogleApiClient mGoogleApiClient;
    private Call<ResponseBody> apiCall;


    private Customer customer;

    private List<Image> outsideList;
    private List<Image> insideList;
    private List<Image> sectionList;
    private List<DateTime> dateTimes;

    private ShowImgAdapter outsideAdapter;
    private ShowImgAdapter insideAdapter;
    private ShowImgAdapter sectionAdapter;

    private int clickedPosition = RecyclerView.NO_POSITION;
    private int dateTimePosition = 0;
    private String selectedImgType = "";
    private String locationFor = "";
    private String timeFor = "";
    private String customerID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);

        customerID = getIntent().getStringExtra(IntentExtras.CUSTOMER_ID);
//        customerID = (customerID == null ? "" : customerID);

//        if(customerID == null || customerID.equals("")){
//
//        }

        init();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.rgTime.setOnCheckedChangeListener(this::onCheckedChanged);

        if(customerID == null || customerID.equals("")){
            dateTimes = prepareDateTime();
            binding.rdbSunday.setChecked(true);

            setImageLists(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        } else {
            fetchCustomerFromDB();
        }

    }

    private void fetchCustomerFromDB(){
        new FetchCustomerTask().execute();
    }


    private void setUI(Customer customer,List<Image> outsideList,List<Image> insideList,List<Image> sectionList){
        binding.etCustName.setText(customer.getCustomerName());
        binding.etPh.setText(customer.getPhNo());
        binding.etEmail.setText(customer.getEmailId());
        binding.etCustAddress.setText(customer.getAddress());
        binding.etWeb.setText(customer.getWebLink());

        binding.etStoreAddress.setText(customer.getStoreAddress());
        binding.etStoreLat.setText(customer.getStoreLat());
        binding.etStoreLng.setText(customer.getStoreLng());

        binding.etRcvAddress.setText(customer.getRcvAddress());
        binding.etRcvLat.setText(customer.getRcvLat());
        binding.etRcvLng.setText(customer.getRcvLng());

        dateTimes = customer.getDateTime() != null ? customer.getDateTime() : prepareDateTime();
        binding.rdbSunday.setChecked(true);

        setImageLists(outsideList,insideList,sectionList);
    }

    private void setImageLists(List<Image> outsideList,List<Image> insideList,List<Image> sectionList){
        binding.rvOutside.setAdapter(
                outsideAdapter = new ShowImgAdapter(this,this,
                        this.outsideList = outsideList));

        binding.rvInside.setAdapter(
                insideAdapter = new ShowImgAdapter(this,this,
                        this.insideList = insideList));

        binding.rvSection.setAdapter(
                sectionAdapter = new ShowImgAdapter(this,this,
                        this.sectionList = sectionList));
    }

    private List<DateTime> prepareDateTime(){
        List<DateTime> dateTimes;
        dateTimes = new ArrayList<DateTime>(){{
            add(new DateTime("SUN","",""));
            add(new DateTime("MON","",""));
            add(new DateTime("TUE","",""));
            add(new DateTime("WED","",""));
            add(new DateTime("THU","",""));
            add(new DateTime("FRI","",""));
            add(new DateTime("SAT","",""));
        }};

        return dateTimes;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tvSave:
                new SaveTask().execute();
//                startActivity(new Intent(this,LocationActivity.class));
                break;
            case R.id.tvCancel:
                onBackPressed();
                break;
            case R.id.tvTimeFrom:
                timeFor = getString(R.string.time_from);
                pickTime(binding.tvTimeFrom);
                break;
            case R.id.tvTimeTo:
                timeFor = getString(R.string.time_to);
                pickTime(binding.tvTimeTo);
                break;
            case R.id.imvOutSide:
                if(outsideList.size()==3) return;
                selectedImgType = OUTSIDE_PIC;
                requestPermission();
                break;
            case R.id.imvInside:
                if(insideList.size()==3) return;
                selectedImgType = INSIDE_PIC;
                requestPermission();
                break;
            case R.id.imvSection:
                if(sectionList.size()==3) return;
                selectedImgType = SECTION_PIC;
                requestPermission();
                break;
            case R.id.imbStoreAddress:
            case R.id.imbStoreLat:
            case R.id.imbStoreLng:
                requestLocationPermission("store_entrance");
                break;
            case R.id.imbRcvAddress:
            case R.id.imbRcvLat:
            case R.id.imbRcvLng:
                requestLocationPermission("receiving_entrance");
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
    public void onItemClick(int position, String imgType) {
        this.clickedPosition = position;
        this.selectedImgType = imgType;
//        requestPermission();
    }

    @Override
    public void onDeleteClick(int position, String imgType) {
        switch (imgType){
            case OUTSIDE_PIC:
                outsideList.remove(position);
                outsideAdapter.notifyDataSetChanged();
                break;
            case INSIDE_PIC:
                insideList.remove(position);
                insideAdapter.notifyDataSetChanged();
                break;
            case SECTION_PIC:
                sectionList.remove(position);
                sectionAdapter.notifyDataSetChanged();
                break;
        }
    }

    private synchronized void buildGoogleApiClient(String locationFor) {
        if (mGoogleApiClient != null) return;
        this.locationFor = locationFor;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            CropImage.startPickImageActivity(AddCustomerActivity.this);
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
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

        String time = new StringBuilder().append(String.format("%02d", hourOfDay)).append(" : ")
                .append(String.format("%02d", minute)).append(" ").append(format).toString();
        textView.setText(time);

        if(timeFor.equals(getString(R.string.time_from))){
            dateTimes.get(dateTimePosition).setFromTime(time);
        }

        if(timeFor.equals(getString(R.string.time_to))){
            dateTimes.get(dateTimePosition).setToTime(time);
        }
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1, 1)
                .start(this);
    }

    private void addPictureInList(String imgType, Uri uri){
        File file = FileUtils.getFile(this, uri);
        String imgPath = new ImageCompresseor().compressImage(file.getPath());
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);

        switch (imgType){
            case OUTSIDE_PIC:
//                outsideList.add(new Image(imgType,uri));
                outsideList.add(new Image(imgType, BitmapManager.bitmapToByte(bitmap),bitmap));
                outsideAdapter.notifyDataSetChanged();
                break;
            case INSIDE_PIC:
//                insideList.add(new Image(imgType,uri));
                insideList.add(new Image(imgType, BitmapManager.bitmapToByte(bitmap),bitmap));
                insideAdapter.notifyDataSetChanged();
                break;
            case SECTION_PIC:
//                sectionList.add(new Image(imgType,uri));
                sectionList.add(new Image(imgType, BitmapManager.bitmapToByte(bitmap),bitmap));
                sectionAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mGoogleApiClient.stopAutoManage(AddCustomerActivity.this);
        if(mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
        mGoogleApiClient = null;

       setLocation(location);
    }

    private void setLocation(Location location){
        if(locationFor.equals("store_entrance")){
            binding.etStoreLat.setText(String.valueOf(location.getLatitude()));
            binding.etStoreLng.setText(String.valueOf(location.getLongitude()));
        }

        if(locationFor.equals("receiving_entrance")){
            binding.etRcvLat.setText(String.valueOf(location.getLatitude()));
            binding.etRcvLng.setText(String.valueOf(location.getLongitude()));
        }

        getAddressFromServer(location);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri imageUri = CropImage.getPickImageResultUri(this, data);
                    startCropImageActivity(imageUri);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {
                        Uri fileUri = result.getUri();

                        addPictureInList(selectedImgType,fileUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private Customer collectCustomer(){
        Customer customer = new Customer();

        customer.setId(String.valueOf(System.currentTimeMillis()));
        customer.setCustomerName(binding.etCustName.getText().toString().trim());
        customer.setPhNo(binding.etPh.getText().toString().trim());
        customer.setEmailId(binding.etEmail.getText().toString().trim());
        customer.setAddress(binding.etCustAddress.getText().toString().trim());
        customer.setWebLink(binding.etWeb.getText().toString().trim());

        customer.setStoreAddress(binding.etStoreAddress.getText().toString().trim());
        customer.setStoreLat(binding.etStoreLat.getText().toString().trim());
        customer.setStoreLng(binding.etStoreLng.getText().toString().trim());

        customer.setRcvAddress(binding.etRcvAddress.getText().toString().trim());
        customer.setRcvLat(binding.etRcvLat.getText().toString().trim());
        customer.setRcvLng(binding.etRcvLng.getText().toString().trim());

        customer.setDateTime(dateTimes);

        return customer;
    }

    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

//            long timeMillis = System.currentTimeMillis();

            Customer customer = collectCustomer();
//            customer.setCustomerName(binding.etCustName.getText().toString().trim());
//            customer.setId(String.valueOf(timeMillis));
//            customer.setPhNo(binding.etPh.getText().toString().trim());
//            customer.setEmailId(binding.etEmail.getText().toString().trim());
//            customer.setAddress(binding.etCustAddress.getText().toString().trim());
//            customer.setWebLink(binding.etWeb.getText().toString().trim());
//
//            customer.setDateTime(dateTimes);

            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .customerDao().insert(customer);


            for (Image image : outsideList){
                image.setCustomerId(customer.getId());
            }

            for (Image image : insideList){
                image.setCustomerId(customer.getId());
            }

            for (Image image : sectionList){
                image.setCustomerId(customer.getId());
            }

            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .imageDao().insertAll(outsideList);

            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .imageDao().insertAll(insideList);

            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .imageDao().insertAll(sectionList);

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


    class FetchCustomerTask extends AsyncTask<Void, Void, Void> {
        private Customer customer;
        private List<Image> outsideList;
        private List<Image> insideList;
        private List<Image> sectionList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("####################### onPreExecute #######################");
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
            System.out.println("####################### onPostExecute #######################");
            setUI(customer,outsideList,insideList,sectionList);
        }
    }

    private void requestLocationPermission(String locationFor) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            buildGoogleApiClient(locationFor);
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void initLocationRequest() {
        if (ContextCompat.checkSelfPermission(AddCustomerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AddCustomerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestLocationPermission(locationFor);

//            ActivityCompat.requestPermissions(AddCustomerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {

            if (mGoogleApiClient != null) {
                if (mGoogleApiClient.isConnected()) {

                    LocationRequest locationRequest = LocationRequest.create();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setSmallestDisplacement(5);

                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);


                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    if (ContextCompat.checkSelfPermission(AddCustomerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                            ContextCompat.checkSelfPermission(AddCustomerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

//                                        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                                        System.out.println("mLastLocation:" + mLastLocation);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        status.startResolutionForResult(AddCustomerActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    private void getAddressFromServer(Location location) {
//        CustomLoader.getInstance(this).show();
        String GET_ADDRESS="https://maps.googleapis.com/maps/api/geocode/json?latlng=latitude,longitude&key=apiKey";

        if(apiCall != null) apiCall.cancel();
        apiCall = ApiClient.getClient(this).create(ApiInterface.class).getAddress(GET_ADDRESS.
                replace("latitude",String.valueOf(location.getLatitude()))
                .replace("longitude",String.valueOf(location.getLongitude()))
                .replace("apiKey",getString(R.string.map_api_key)));

        apiCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                        CustomLoader.dismissLoader();
                try {
                    String responseBody=response.body().string();
                    System.out.println("response:"+responseBody);
                    LocationResponse result = new Gson().fromJson(responseBody, LocationResponse.class);

                    if (result.getStatus().equals("OK")) {
                        if(locationFor.equals("store_entrance")){
                            binding.etStoreAddress.setText(result.getResults().get(0).getFormattedAddress());
                            binding.etStoreLat.setText(String.valueOf(location.getLatitude()));
                            binding.etStoreLng.setText(String.valueOf(location.getLongitude()));
                        }

                        if(locationFor.equals("receiving_entrance")){
                            binding.etRcvAddress.setText(result.getResults().get(0).getFormattedAddress());
                            binding.etRcvLat.setText(String.valueOf(location.getLatitude()));
                            binding.etRcvLng.setText(String.valueOf(location.getLongitude()));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                        CustomLoader.dismissLoader();
            }
        });
    }



}
