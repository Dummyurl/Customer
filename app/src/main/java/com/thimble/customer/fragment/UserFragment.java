package com.thimble.customer.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.MainActivity;
import com.thimble.customer.R;
import com.thimble.customer.activity.ShowImagesActivity;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.databinding.FragmentUserBinding;
import com.thimble.customer.db.model.Customer;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment implements CustomerAdapter.OnItemClickListner {


    private FragmentUserBinding binding;

    private CustomerAdapter adapter;
    private List<Customer> customers;


    public UserFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

        customers = new ArrayList<>();
        customers.add(new Customer("Satyabrta Pasari"));
        customers.add(new Customer("Debaprasad kundu"));
        customers.add(new Customer("Subrata Pasari"));
        customers.add(new Customer("Puja Nandi"));
        customers.add(new Customer("Priti Nandi"));
        customers.add(new Customer("Priya Nandi"));
        customers.add(new Customer("Hasi Nayak"));
        customers.add(new Customer("Shovan Nayak"));
        customers.add(new Customer("Hello Abc"));
        customers.add(new Customer("Ding dong"));
        customers.add(new Customer("Ping pong"));

        adapter = new CustomerAdapter(getActivity(),this,customers);
        binding.rvUser.setAdapter(adapter);

        return binding.getRoot();
    }

    public void filterList(CharSequence charSequence){
        adapter.getFilter().filter(charSequence);
    }



    @Override
    public void onClick(int position) {
        startActivity(new Intent(getActivity(),ShowImagesActivity.class));

    }

    @Override
    public void onLongClick(int position) {
        customers.get(position).setSelect(true);
        adapter.notifyDataSetChanged();

        ((MainActivity) getActivity()).onLongClick();
    }
}



