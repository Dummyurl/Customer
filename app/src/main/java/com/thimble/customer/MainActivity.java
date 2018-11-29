package com.thimble.customer;


import android.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.fabAdd.setOnClickListener(this::OnClick);
//
//        setSupportActionBar(binding.toolbar);
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
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_select_all:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
