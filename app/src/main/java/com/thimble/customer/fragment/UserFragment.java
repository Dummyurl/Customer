package com.thimble.customer.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.activity.MainActivity;
import com.thimble.customer.R;
import com.thimble.customer.activity.ShowImagesActivity;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.databinding.FragmentUserBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.db.model.Image;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment implements CustomerAdapter.OnItemClickListner {


    private FragmentUserBinding binding;

    private CustomerAdapter adapter;
    private List<Customer> customers;
    private boolean isMultiSelect = false;
    private int selectedCount = 0;


    public UserFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

//        customers = new ArrayList<>();
//        customers.add(new Customer("Satyabrta Pasari"));
//        customers.add(new Customer("Debaprasad kundu"));
//        customers.add(new Customer("Subrata Pasari"));
//        customers.add(new Customer("Puja Nandi"));
//        customers.add(new Customer("Priti Nandi"));
//        customers.add(new Customer("Priya Nandi"));
//        customers.add(new Customer("Hasi Nayak"));
//        customers.add(new Customer("Shovan Nayak"));
//        customers.add(new Customer("Hello Abc"));
//        customers.add(new Customer("Ding dong"));
//        customers.add(new Customer("Ping pong"));


        setUI(customers = new ArrayList<>());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        fetchCustomerListFromDB();
    }

    private void setUI(List<Customer> customers){
        if(adapter == null){
            adapter = new CustomerAdapter(getActivity(),this,customers);
            binding.rvUser.setAdapter(adapter);

        }else {
            adapter.notifyDataSetChanged();
        }

        if(customers.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void fetchCustomerListFromDB(){
        new FetchCustomerTask().execute();
    }


    public void doFilter(CharSequence charSequence){
        adapter.getFilter().filter(charSequence);
    }


    @Override
    public void onClick(int position) {
//        if(isMultiSelect){
//            if(customers.get(position).isSelected()){
//                customers.get(position).setSelected(false);
//                selectedCount --;
//            }else {
//                customers.get(position).setSelected(true);
//                selectedCount ++;
//            }
//            adapter.notifyItemChanged(position);
//            ((MainActivity) getActivity()).onLongClick(isMultiSelect = (selectedCount != 0));
//        }else {
//            startActivity(new Intent(getActivity(),ShowImagesActivity.class));
//        }
    }

    @Override
    public void onLongClick(int position) {
//        if(isMultiSelect) return;
//        customers.get(position).setSelected(true);
//        adapter.notifyItemChanged(position);

//        ((MainActivity) getActivity()).onLongClick(isMultiSelect = true);
//        isMultiSelect = true;
//        selectedCount ++;

//        doOnLongClickTask(position);
    }

    private void doOnLongClickTask(int position){
//        adapter.notifyItemChanged(position);
//        ((MainActivity) getActivity()).onLongClick();
    }

    public void doCancelMultiSelect(){
//        if (!isMultiSelect) return;
        for (Customer customer : customers){
            customer.setSelected(false);
        }
        adapter.notifyDataSetChanged();
        adapter.setMultiSelect(isMultiSelect = false);
        adapter.setSelectedCount(selectedCount = 0);
//        isMultiSelect = false;
//        selectedCount = 0;
    }



    class FetchCustomerTask extends AsyncTask<Void, Void, List<Customer>> {

        @Override
        protected List<Customer> doInBackground(Void... voids) {
            List<Customer> customers = DBClient
                    .getInstance(getActivity())
                    .getAppDB()
                    .customerDao()
                    .getAllCustomers();

            return customers;
        }

        @Override
        protected void onPostExecute(List<Customer> customers) {
            super.onPostExecute(customers);
            UserFragment.this.customers.clear();
            UserFragment.this.customers.addAll(customers);
            setUI(UserFragment.this.customers);
        }
    }


}



