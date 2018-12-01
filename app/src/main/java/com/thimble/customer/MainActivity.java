package com.thimble.customer;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thimble.customer.activity.EditImageActivity;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.adapter.ViewPagerAdapter;
import com.thimble.customer.db.AppDB;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.dao.CustomerDao;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.DateTime;

import java.util.ArrayList;
import java.util.List;
import com.thimble.customer.databinding.ActivityMainBinding;
import com.thimble.customer.fragment.UserFragment;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    private RelativeLayout rlRevealItems;
    boolean hidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.fabAdd.setOnClickListener(this::OnClick);

        rlRevealItems = binding.rlRevealItems;
//
        setSupportActionBar(binding.toolbar);
//
//        binding.rvUser.setAdapter(new CustomerAdapter(this));



        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);


        new SaveTask().execute();


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new UserFragment(), "Server");
        adapter.addFrag(new UserFragment(), "Local");
        viewPager.setAdapter(adapter);
    }



    private void openSearch() {
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
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        rlRevealItems.setVisibility(View.INVISIBLE);
                        hidden = true;
//                        etSearch.setText("");
                        rlRevealItems.requestFocus();

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
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
            case R.id.imbMenu:
                break;
            case R.id.imbSearch:
            case R.id.imbCancelSrch:
                openSearch();
                break;

        }

    }




    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Customer customer = new Customer();
            customer.setAddress("setAddress");
            customer.setEmailId("setAddress");
            customer.setLocAddress("setLocAddress");
            customer.setUserName("setAddress");

            DateTime dateTime = new DateTime();
            dateTime.setDate("nbjv,mnxc,mvn");

            customer.setDateTime(dateTime);

            //adding to database
            DBClient.getInstance(getApplicationContext()).getAppDB()
                    .customerDao().insert(customer);

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


    class GetTasks extends AsyncTask<Void, Void, List<Customer>> {

        @Override
        protected List<Customer> doInBackground(Void... voids) {
            List<Customer> taskList = DBClient
                    .getInstance(getApplicationContext())
                    .getAppDB()
                    .customerDao()
                    .getAll();
            return taskList;
        }

        @Override
        protected void onPostExecute(List<Customer> tasks) {
            super.onPostExecute(tasks);

            System.out.println("tasks:"+tasks.size());
        }
    }



    public void OnClick(View view){
        switch (view.getId()){
            case R.id.fabAdd:
                startActivity(new Intent(this,EditImageActivity.class));

                new GetTasks().execute();


                break;
        }
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
                openSearch();
               return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
