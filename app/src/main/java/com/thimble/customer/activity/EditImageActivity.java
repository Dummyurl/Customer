package com.thimble.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.base.AppConstant;
import com.thimble.customer.databinding.ActivityEditImagesBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.Image;
import com.thimble.customer.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.thimble.customer.base.AppConstant.CAMERA_PERMISSION_REQUEST_CODE;
import static com.thimble.customer.base.AppConstant.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE;
import static com.thimble.customer.base.AppConstant.INSIDE_PIC;
import static com.thimble.customer.base.AppConstant.OUTSIDE_PIC;
import static com.thimble.customer.base.AppConstant.SECTION_PIC;

public class EditImageActivity extends AppCompatActivity  implements ShowImgAdapter.onItemClickListener {

    private ActivityEditImagesBinding binding;


    private List<Image> outsideList;
    private List<Image> insideList;
    private List<Image> sectionList;

    private ShowImgAdapter outsideAdapter;
    private ShowImgAdapter insideAdapter;
    private ShowImgAdapter sectionAdapter;

    private int clickedPosition = RecyclerView.NO_POSITION;
    private String selectedImgType = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_images);

        init();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.rvOutside.setAdapter(
                outsideAdapter = new ShowImgAdapter(this,this,
                        outsideList = new ArrayList<>()));

        binding.rvInside.setAdapter(
                insideAdapter = new ShowImgAdapter(this,this,
                        insideList = new ArrayList<>()));

        binding.rvSection.setAdapter(
                sectionAdapter = new ShowImgAdapter(this,this,
                        sectionList = new ArrayList<>()));
    }


//    private List<Image> getEmptyImgList(String imgType){
//        return new ArrayList<Image>(){{
//            for(int i=0 ; i<3 ;i++){
//                add(new Image(imgType));
//            }
//        }};
//    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.imvOutSide:
                if(outsideList.size()==3) return;
                selectedImgType = AppConstant.OUTSIDE_PIC;
                requestPermission();
                break;
            case R.id.imvInside:
                if(insideList.size()==3) return;
                selectedImgType = AppConstant.INSIDE_PIC;
                requestPermission();
                break;
            case R.id.imvSection:
                if(sectionList.size()==3) return;
                selectedImgType = AppConstant.SECTION_PIC;
                requestPermission();
                break;
            case R.id.tvSave:
                new SaveTask().execute();
//                startActivity(new Intent(this,EditDateTimeAddressActivity.class));
                break;
            case R.id.tvCancel:
                onBackPressed();
                break;
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
                            CropImage.startPickImageActivity(EditImageActivity.this);
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



    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1, 1)
                .start(this);
    }


    private void addPictureInList(String imgType, Uri uri){
//        File file = FileUtils.getFile(this, uri);
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

        switch (imgType){
            case OUTSIDE_PIC:
                outsideList.add(new Image(imgType,uri));
//                outsideList.add(new Image(imgType,uri,getBytes(bitmap)));
                outsideAdapter.notifyDataSetChanged();
                break;
            case INSIDE_PIC:
                insideList.add(new Image(imgType,uri));
//                insideList.add(new Image(imgType,uri,getBytes(bitmap)));
                insideAdapter.notifyDataSetChanged();
                break;
            case SECTION_PIC:
                sectionList.add(new Image(imgType,uri));
//                sectionList.add(new Image(imgType,uri,getBytes(bitmap)));
                sectionAdapter.notifyDataSetChanged();
                break;
        }
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

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            long timeMillis = System.currentTimeMillis();

            Customer customer = new Customer();
            customer.setUserName(binding.etCustName.getText().toString().trim());
            customer.setId(String.valueOf(timeMillis));

            for (Image image : outsideList){
                image.setUserId(String.valueOf(timeMillis));
            }

            for (Image image : insideList){
                image.setUserId(String.valueOf(timeMillis));
            }

            for (Image image : sectionList){
                image.setUserId(String.valueOf(timeMillis));
            }


//            customer.setEmailId("setAddress");
//            customer.setLocAddress("setLocAddress");
//            customer.setUserName("setAddress");

//            DateTime dateTime = new DateTime();
//            dateTime.setDate("nbjv,mnxc,mvn");
//
//            customer.setDateTime(dateTime);

            //adding to database
            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .customerDao().insert(customer);

            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .imageDao().insertAll(outsideList);

//            DBClient.getInstance(getApplicationContext()).getAppDB()
//                    .imageDao().insertAll(insideList);
//
//            DBClient.getInstance(getApplicationContext()).getAppDB()
//                    .imageDao().insertAll(sectionList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();


            new GetTasks().execute();
        }
    }

    class GetTasks extends AsyncTask<Void, Void, List<Customer>> {

        @Override
        protected List<Customer> doInBackground(Void... voids) {
            List<Customer> taskList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .customerDao()
                    .getAll();


            List<Image> imgList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .imageDao()
                    .getAll();

            if(true){

            }

            return taskList;
        }

        @Override
        protected void onPostExecute(List<Customer> tasks) {
            super.onPostExecute(tasks);

            System.out.println("tasks:"+tasks.size());
        }
    }





}
