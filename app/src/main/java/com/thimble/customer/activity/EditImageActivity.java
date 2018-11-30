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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.base.AppConstant;
import com.thimble.customer.databinding.ActivityEditImagesBinding;
import com.thimble.customer.model.Image;
import com.thimble.customer.util.CaptureImage;

import java.util.ArrayList;
import java.util.List;

import static com.thimble.customer.base.AppConstant.CAMERA_PERMISSION_REQUEST_CODE;
import static com.thimble.customer.base.AppConstant.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE;
import static com.thimble.customer.base.AppConstant.INSIDE_PIC;
import static com.thimble.customer.base.AppConstant.OUTSIDE_PIC;
import static com.thimble.customer.base.AppConstant.SECTION_PIC;

public class EditImageActivity extends AppCompatActivity  implements ShowImgAdapter.onItemClickListener {

    private ActivityEditImagesBinding binding;

    private Uri fileUri;

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

        binding.rvOutside.setAdapter(
                outsideAdapter = new ShowImgAdapter(this,this,
                        outsideList = getEmptyImgList(String.valueOf(OUTSIDE_PIC))));

        binding.rvInside.setAdapter(
                insideAdapter = new ShowImgAdapter(this,this,
                        insideList = getEmptyImgList(String.valueOf(INSIDE_PIC))));

        binding.rvSection.setAdapter(
                sectionAdapter = new ShowImgAdapter(this,this,
                        sectionList = getEmptyImgList(SECTION_PIC)));
    }


    private List<Image> getEmptyImgList(String imgType){
        return new ArrayList<Image>(){{
            for(int i=0 ; i<3 ;i++){
                add(new Image(imgType));
            }
        }};
    }


    public void OnClick(View view){
        switch (view.getId()){
            case R.id.imvOutSide:
                requestPermission();
                break;
            case R.id.imvInside:
                break;
            case R.id.tvSection:
                break;

        }
    }

    @Override
    public void onItemClick(int position, String imgType) {
        this.clickedPosition = position;
        this.selectedImgType = imgType;
        requestPermission();
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
                        Uri fileUri = result.getUri();
                        switch (selectedImgType){
                            case OUTSIDE_PIC:
                                outsideList.get(clickedPosition).setImgUri(fileUri);
                                outsideAdapter.notifyItemChanged(clickedPosition);
                                break;
                            case INSIDE_PIC:
                                insideList.get(clickedPosition).setImgUri(fileUri);
                                insideAdapter.notifyItemChanged(clickedPosition);
                                break;
                            case SECTION_PIC:
                                sectionList.get(clickedPosition).setImgUri(fileUri);
                                sectionAdapter.notifyItemChanged(clickedPosition);
                                break;
                        }
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
