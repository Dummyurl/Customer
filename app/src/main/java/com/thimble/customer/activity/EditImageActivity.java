package com.thimble.customer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.databinding.ActivityEditImagesBinding;
import com.thimble.customer.util.CaptureImage;

import static com.thimble.customer.base.AppConstant.CAMERA_PERMISSION_REQUEST_CODE;
import static com.thimble.customer.base.AppConstant.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE;

public class EditImageActivity extends AppCompatActivity  {

    private ActivityEditImagesBinding binding;

    private Uri fileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_images);

        binding.rvOutside.setAdapter(new ShowImgAdapter(this));
        binding.rvInside.setAdapter(new ShowImgAdapter(this));
        binding.rvSection.setAdapter(new ShowImgAdapter(this));
    }



    public void OnClick(View view){
        switch (view.getId()){
            case R.id.imvOutSide:
                captureImage();
                break;
            case R.id.imvInside:
                break;
            case R.id.tvSection:
                break;

        }
    }



    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this,  Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);

        }else {

            CropImage.startPickImageActivity(this);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1, 1)
                .start(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                }
                break;
            case EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureImage();

                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@ onActivityResult @@@@@@@@@@@@@@@@@@@@@@@@@@");
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
                        fileUri = result.getUri();
                        String selectedFilePath = CaptureImage.getPath(this, result.getUri());
                        String selectedFileName = selectedFilePath.substring(selectedFilePath.lastIndexOf("/") + 1);

                        Bitmap bitmap = BitmapFactory.decodeFile(selectedFilePath);
                        binding.imvInside.setImageBitmap(bitmap);

//                        binding.etKYC.setText(selectedFilePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    //Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
