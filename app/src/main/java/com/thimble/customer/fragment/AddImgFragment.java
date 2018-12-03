package com.thimble.customer.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.thimble.customer.R;
import com.thimble.customer.activity.EditImageActivity;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.base.AppConstant;
import com.thimble.customer.databinding.FragmentAddImagesBinding;
import com.thimble.customer.databinding.FragmentUserBinding;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.Image;
import com.thimble.customer.util.BitmapManager;
import com.thimble.customer.util.FileUtils;
import com.thimble.customer.view.CustomLoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.thimble.customer.base.AppConstant.INSIDE_PIC;
import static com.thimble.customer.base.AppConstant.OUTSIDE_PIC;
import static com.thimble.customer.base.AppConstant.SECTION_PIC;


public class AddImgFragment extends Fragment implements ShowImgAdapter.onItemClickListener {


    private FragmentAddImagesBinding binding;

    private List<Image> outsideList;
    private List<Image> insideList;
    private List<Image> sectionList;

    private ShowImgAdapter outsideAdapter;
    private ShowImgAdapter insideAdapter;
    private ShowImgAdapter sectionAdapter;

    private int clickedPosition = RecyclerView.NO_POSITION;
    private String selectedImgType = "";


    public AddImgFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_images, container, false);



        init();

        return binding.getRoot();
    }


    private void init(){
        binding.imvOutSide.setOnClickListener(this::onClick);
        binding.imvInside.setOnClickListener(this::onClick);
        binding.imvSection.setOnClickListener(this::onClick);

        binding.rvOutside.setAdapter(
                outsideAdapter = new ShowImgAdapter(getActivity(),this,
                        outsideList = new ArrayList<>()));

        binding.rvInside.setAdapter(
                insideAdapter = new ShowImgAdapter(getActivity(),this,
                        insideList = new ArrayList<>()));

        binding.rvSection.setAdapter(
                sectionAdapter = new ShowImgAdapter(getActivity(),this,
                        sectionList = new ArrayList<>()));
    }


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
        }
    }

    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            CropImage.startPickImageActivity(getActivity(),AddImgFragment.this);
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }



    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1, 1)
                .start(getActivity(),AddImgFragment.this);
    }


    private void addPictureInList(String imgType, Uri uri){
        File file = FileUtils.getFile(getActivity(), uri);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());

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

        CustomLoder.dismisCustomProgressBar();
    }





    @Override
    public void onItemClick(int position, String imgType) {
        this.clickedPosition = position;
        this.selectedImgType = imgType;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);
                    startCropImageActivity(imageUri);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    try {
//                        CustomLoder.showCustomProgressBar(getActivity());
                        Uri fileUri = result.getUri();
//                        new Handler().postDelayed(() -> {
                            addPictureInList(selectedImgType,fileUri);
//                        },300);

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



