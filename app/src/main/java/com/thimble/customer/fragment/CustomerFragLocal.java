package com.thimble.customer.fragment;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.R;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.databinding.FragmentUserBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.model.CustomerItem;

import java.util.ArrayList;
import java.util.List;


public class CustomerFragLocal extends Fragment implements CustomerAdapter.OnItemClickListner {


    private FragmentUserBinding binding;

    private CustomerAdapter adapter;
    private List<CustomerItem> customers;
    private boolean isMultiSelect = false;
    private int selectedCount = 0;


    public CustomerFragLocal() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

        setUI(customers = new ArrayList<>());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        fetchCustomerListFromDB();
    }

    private void setUI(List<CustomerItem> customers){
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

    public void deleteCustomer(){
        new DeleteCustomerTask().execute();
    }



    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }


    public void doCancelMultiSelect(){
        for (CustomerItem customer : customers){
            customer.setSelected(false);
        }
        adapter.notifyDataSetChanged();
        adapter.setMultiSelect(isMultiSelect = false);
        adapter.setSelectedCount(selectedCount = 0);
    }



    class FetchCustomerTask extends AsyncTask<Void, Void, List<CustomerItem>> {
        @Override
        protected List<CustomerItem> doInBackground(Void... voids) {
            List<CustomerItem> customers  = DBClient
                    .getInstance(getActivity())
                    .getAppDB()
                    .customerDao()
                    .getAllCustomers(0);

            return customers;
        }

        @Override
        protected void onPostExecute(List<CustomerItem> customers) {
            super.onPostExecute(customers);
            CustomerFragLocal.this.customers.clear();
            CustomerFragLocal.this.customers.addAll(customers);
            setUI(CustomerFragLocal.this.customers);
        }
    }

    class DeleteCustomerTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for(CustomerItem customer : customers){
                if(customer.isSelected()){
                    DBClient.getInstance(getActivity())
                            .getAppDB()
                            .customerDao()
                            .deleteCustomers(customer.getId());
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            new FetchCustomerTask().execute();
        }
    }


}



