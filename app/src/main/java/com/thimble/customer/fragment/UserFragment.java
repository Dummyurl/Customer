package com.thimble.customer.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.R;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.databinding.FragmentUserBinding;


public class UserFragment extends Fragment {

    private CustomerAdapter adapter;

    private FragmentUserBinding binding;

    public UserFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

        adapter = new CustomerAdapter(getActivity());

        binding.rvUser.setAdapter(adapter);

        return binding.getRoot();
    }
}



