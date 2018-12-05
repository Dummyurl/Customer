package com.thimble.customer.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
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
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thimble.customer.databinding.ActivityMainBinding;
import com.thimble.customer.fragment.UserFragment;
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
                int currentItem = binding.viewpager.getCurrentItem();
                UserFragment userFrag = (UserFragment)adapter.getItem(currentItem);
                userFrag.doFilter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


//        new SaveTask().execute();

        details();
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
        adapter.addFrag(new UserFragment(), "Server");
        adapter.addFrag(new UserFragment(), "Local");
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
                UserFragment user = (UserFragment)adapter.getItem(binding.viewpager.getCurrentItem());
                user.deleteCustomer();
                hideMultiSelectMode();
                break;
            case R.id.imbCancelSelection:
                UserFragment userFrag = (UserFragment)adapter.getItem(binding.viewpager.getCurrentItem());
                userFrag.doCancelMultiSelect();
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
        UserFragment userFrag = (UserFragment)adapter.getItem(binding.viewpager.getCurrentItem());
        userFrag.doCancelMultiSelect();
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



    private void details() {
        CustomLoder.showCustomProgressBar(this);

        Map<String, Object> params = new HashMap<>();
        params.put(ApiHelper.BROKER_ID, "015937");
//        params.put(ApiHelper.SALESMAN_ID, "015937");
        params.put(ApiHelper.COMPANY_ID, "2");

        String header = "jiNaduK-WxFTmRLc5QV-yfoWx8QwNpJLVVT1-Q39nNKZf4SMAquTZKum5yNseN9TLpyB07G0utbIpC3l8pIoF0C4IOKWlabMD-XldZAUn5knhhsDZa4boHaA-8isSQk1BhXae9pEuaGhfrde2ANVQeikEJWAbK2TSVJGmqYgnTuFMSk7_5HGUkbaV5o1SLAe70VJrSKWZivo-bCcEYQUM6oPkfk8opR2M6I7BkvA_6kJJOuTFpPhi40lsNhhGktWvWs9xArqFDb4L3dCB3xJV-Dqn3QNgBCgbJMKWqGBSbyiw1T5k01B7JWn87nsuIKvKkGPCTaUxk7XdnLA2NxluyIaTOfNZTkC0m5PHgjdwWoHSlAYqFaTzYH6XyHU0nFSx5YqNcJ1VzXhJfQMwywy7jd_qiZd5-Qi3I2DlkUs5BxbjAlnCk99vv95Ezvb4Cpa8AvpX_d_KClihQFlhnodWp1qSRxbmQ2ZRDQncTMICHHoOo-Vp1xMOR5E8-cHaHgjoAxPJlOPV9IObqgBaM5txdhagfVbLx0QzoqlwszITL2z0qWIy_WwgbHnPX2ofsKZLRjfg";

        ApiClient.getClient(this).create(ApiInterface.class).
                details(header,params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                System.out.println("response:"+response);
                CustomLoder.dismisCustomProgressBar();
                try {
                    String responseBody=response.body().string();
                    System.out.println("responseBody:"+responseBody);

//                    if(response.code() == 200){
//                        LoginResponse login = new Gson().fromJson(responseBody,LoginResponse.class);
//                        if(login.getPayload().get(0).getAuthorization().toLowerCase().equals("success")){
//
//                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                            finish();
//
//                        }
//                    }

//                    switch (login.getStatus()){
//                        case STATUS_SUCCESS:
//
//                            AppClass.getInstance().setUserData(login.getData());
//
//                            startActivity(new Intent(LystantLoginActivity.this,MainActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
//                                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                                            Intent.FLAG_ACTIVITY_NEW_TASK));
//
//                            finish();
//
//                            break;
//                        case STATUS_MISSING:
//                        case STATUS_ERROR:
//                            Toast.makeText(LystantLoginActivity.this, login.getMsg(), Toast.LENGTH_SHORT).show();
//                            break;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
                CustomLoder.dismisCustomProgressBar();
            }
        });
    }
}
