package com.thimble.customer.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thimble.customer.R;
import com.thimble.customer.adapter.ViewPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import com.thimble.customer.databinding.ActivityMainBinding;
import com.thimble.customer.fragment.CustomerFragLocal;
import com.thimble.customer.fragment.CustomerFragServer;
import com.thimble.customer.rest.ApiClient;
import com.thimble.customer.rest.ApiHelper;
import com.thimble.customer.rest.ApiInterface;
import com.thimble.customer.view.CustomLoder;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{


    private ActivityMainBinding binding;

    private  ViewPagerAdapter adapter;

    private RelativeLayout rlRevealItems;
    private boolean hidden = true;
    private boolean isMultiSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        rlRevealItems = binding.rlRevealItems;
//
        setSupportActionBar(binding.toolbar);


        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.tabs.addOnTabSelectedListener(this);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Fragment frag = adapter.getItem(binding.viewpager.getCurrentItem());
                if(frag instanceof CustomerFragLocal){
                    CustomerFragLocal fragLocal = (CustomerFragLocal)frag;
                    fragLocal.doFilter(charSequence);
                }
                if(frag instanceof CustomerFragServer){
                    CustomerFragServer fragServer = (CustomerFragServer)frag;
                    fragServer.doFilter(charSequence);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


//        new SaveTask().execute();

//        details();
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideMultiSelectMode();
    }

    public void onLongClick(boolean isMultiSelect){
        if(isMultiSelect){
            showMultiSelectMode();
        }else {
            hideMultiSelectMode();
        }
    }

    private void showMultiSelectMode(){
        isMultiSelect = true;
        binding.tvTitle.setVisibility(View.GONE);
        binding.imbSearch.setVisibility(View.GONE);

        binding.imbDelete.setVisibility(View.VISIBLE);
        binding.imbSelectAll.setVisibility(View.VISIBLE);
        binding.imbCancelSelection.setVisibility(View.VISIBLE);
    }

    private void hideMultiSelectMode(){
        isMultiSelect = false;
        binding.tvTitle.setVisibility(View.VISIBLE);
        binding.imbSearch.setVisibility(View.VISIBLE);

        binding.imbDelete.setVisibility(View.GONE);
        binding.imbSelectAll.setVisibility(View.GONE);
        binding.imbCancelSelection.setVisibility(View.GONE);
    }


    private void setPopup(){
        PopupMenu popup = new PopupMenu(MainActivity.this, binding.imbSelectAll);
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });

        popup.show();//showing popup menu
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CustomerFragServer(), "Server");
        adapter.addFrag(new CustomerFragLocal(), "Local");
        viewPager.setAdapter(adapter);
    }



    private void toggleSearch() {
        int cx = (rlRevealItems.getLeft() + rlRevealItems.getRight());
//                int cy = (rlRevealItems.getTop() + rlRevealItems.getBottom())/2;
        int cy = rlRevealItems.getTop();

        int radius = Math.max(rlRevealItems.getWidth(), rlRevealItems.getHeight());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            SupportAnimator animator =
                    ViewAnimationUtils.createCircularReveal(rlRevealItems, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(800);

            SupportAnimator animator_reverse = animator.reverse();

            if (hidden) {
                rlRevealItems.setVisibility(View.VISIBLE);
//                etSearch.requestFocus();
                animator.start();
                hidden = false;
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() { }
                    @Override
                    public void onAnimationEnd() {
                        rlRevealItems.setVisibility(View.INVISIBLE);
                        hidden = true;
//                        etSearch.setText("");
                        rlRevealItems.requestFocus();
                    }
                    @Override
                    public void onAnimationCancel() { }
                    @Override
                    public void onAnimationRepeat() { }
                });
                animator_reverse.start();
            }
        } else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(rlRevealItems, cx, cy, 0, radius);
                rlRevealItems.setVisibility(View.VISIBLE);
//                etSearch.requestFocus();
                anim.start();
                hidden = false;

            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(rlRevealItems, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlRevealItems.setVisibility(View.INVISIBLE);
//                        etSearch.setText("");
                        hidden = true;
                        rlRevealItems.requestFocus();
                    }
                });
                anim.start();
            }
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.imbBack:
                break;
            case R.id.imbSelectAll:
//                setPopup();
                break;
            case R.id.fabAdd:
                startActivity(new Intent(this,AddCustomerActivity.class));
//                startActivity(new Intent(this,EditImageActivity.class));
//                new GetTasks().execute();
                break;
            case R.id.imbSearch:
            case R.id.imbCancelSrch:
                if (hidden){
                    binding.tabs.setVisibility(View.GONE);
                } else {
                    binding.etSearch.setText("");
                    binding.tabs.setVisibility(View.VISIBLE);
                }
                toggleSearch();
                break;
            case R.id.imbDelete:
                Fragment frag = adapter.getItem(binding.viewpager.getCurrentItem());
                if(frag instanceof CustomerFragLocal){
                    CustomerFragLocal fragLocal = (CustomerFragLocal)frag;
                    fragLocal.deleteCustomer();
                }
                if(frag instanceof CustomerFragServer){
                    CustomerFragServer fragServer = (CustomerFragServer)frag;
//                    fragServer.deleteCustomer();
                }
                hideMultiSelectMode();
                break;
            case R.id.imbCancelSelection:
                Fragment fragment = adapter.getItem(binding.viewpager.getCurrentItem());
                if(fragment instanceof CustomerFragLocal){
                    CustomerFragLocal fragLocal = (CustomerFragLocal)fragment;
                    fragLocal.doCancelMultiSelect();
                }
                if(fragment instanceof CustomerFragServer){
                    CustomerFragServer fragServer = (CustomerFragServer)fragment;
                    fragServer.doCancelMultiSelect();
                }
                hideMultiSelectMode();
                break;

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (!isMultiSelect) return;
        Fragment fragment = adapter.getItem(tab.getPosition());
        if(fragment instanceof CustomerFragLocal){
            CustomerFragLocal fragLocal = (CustomerFragLocal)fragment;
            fragLocal.doCancelMultiSelect();
        }
        if(fragment instanceof CustomerFragServer){
            CustomerFragServer fragServer = (CustomerFragServer)fragment;
            fragServer.doCancelMultiSelect();
        }
        hideMultiSelectMode();
        isMultiSelect = false;
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_select_all:
                return true;
            case R.id.menu_search:
                toggleSearch();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
