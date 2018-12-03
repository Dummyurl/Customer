package com.thimble.customer.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thimble.customer.R;
import com.thimble.customer.adapter.ViewPagerAdapter;
import com.thimble.customer.databinding.ActivityAddCustomerBinding;
import com.thimble.customer.fragment.AddImgFragment;
import com.thimble.customer.fragment.DateTimeAddressFragment;
import com.thimble.customer.fragment.LocationFragment;

public class AddCustomerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ActivityAddCustomerBinding binding;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer);

        setupViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(this);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AddImgFragment(), AddImgFragment.class.getSimpleName());
        adapter.addFrag(new DateTimeAddressFragment(),  DateTimeAddressFragment.class.getSimpleName());
        adapter.addFrag(new LocationFragment(), LocationFragment.class.getSimpleName());

        viewPager.setOffscreenPageLimit(adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setAdapter(adapter);
    }

    public void onClick(View view){

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
