package com.thimble.customer.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thimble.customer.R;
import com.thimble.customer.adapter.ShowImgAdapter;
import com.thimble.customer.databinding.ActivityShowImagesBinding;
import com.thimble.customer.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ShowImagesActivity extends AppCompatActivity {

    private ActivityShowImagesBinding binding;

    private List<Image> outsideList;
    private List<Image> insideList;
    private List<Image> sectionList;

    private ShowImgAdapter outsideAdapter;
    private ShowImgAdapter insideAdapter;
    private ShowImgAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_images);

        init();
    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        outsideList = new ArrayList<>();
        outsideList.add(new Image());
        outsideList.add(new Image());
        outsideList.add(new Image());

        binding.rvOutside.setAdapter(
                outsideAdapter = new ShowImgAdapter(this, outsideList ));

        binding.rvInside.setAdapter(
                insideAdapter = new ShowImgAdapter(this, outsideList));

        binding.rvSection.setAdapter(
                sectionAdapter = new ShowImgAdapter(this, outsideList));

    }


    public void onClick(View view ){
        switch (view.getId()){
            case R.id.tvEdit:
                startActivity(new Intent(this,EditImageActivity.class));
                break;
            case R.id.tvDelete:
                startActivity(new Intent(this,DateTimeAddressActivity.class));
                break;
        }
    }
}
