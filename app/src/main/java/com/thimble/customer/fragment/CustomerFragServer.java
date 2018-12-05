package com.thimble.customer.fragment;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.thimble.customer.R;
import com.thimble.customer.adapter.CustomerAdapter;
import com.thimble.customer.base.AppClass;
import com.thimble.customer.databinding.FragmentUserBinding;
import com.thimble.customer.db.DBClient;
import com.thimble.customer.model.CustomerItem;
import com.thimble.customer.model.ListResponse;
import com.thimble.customer.rest.ApiClient;
import com.thimble.customer.rest.ApiHelper;
import com.thimble.customer.rest.ApiInterface;
import com.thimble.customer.view.CustomLoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerFragServer extends Fragment implements CustomerAdapter.OnItemClickListner {


    private FragmentUserBinding binding;

    private CustomerAdapter adapter;
    private List<CustomerItem> customers;
    private boolean isMultiSelect = false;
    private int selectedCount = 0;


    public CustomerFragServer() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);

        setUI(customers = new ArrayList<>());

        loadCustomers();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

//        fetchCustomerListFromDB();
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
        for (CustomerItem customer : customers){
            customer.setSelected(false);
        }
        adapter.notifyDataSetChanged();
        adapter.setMultiSelect(isMultiSelect = false);
        adapter.setSelectedCount(selectedCount = 0);
//        isMultiSelect = false;
//        selectedCount = 0;
    }

    class FetchCustomerTask extends AsyncTask<Void, Void, List<CustomerItem>> {
        @Override
        protected List<CustomerItem> doInBackground(Void... voids) {
            List<CustomerItem> customers  = DBClient
                    .getInstance(getActivity())
                    .getAppDB()
                    .customerDao()
                    .getAllCustomers();

            return customers;
        }

        @Override
        protected void onPostExecute(List<CustomerItem> customers) {
            super.onPostExecute(customers);
            CustomerFragServer.this.customers.clear();
            CustomerFragServer.this.customers.addAll(customers);
            setUI(CustomerFragServer.this.customers);
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


    private void loadCustomers() {
        CustomLoder.showCustomProgressBar(getActivity());

        Map<String, Object> params = new HashMap<>();
        params.put(ApiHelper.BROKER_ID, "015937");
//        params.put(ApiHelper.SALESMAN_ID, "015937");
        params.put(ApiHelper.COMPANY_ID, "2");

        String header = AppClass.getInstance().getUserData().getTokenType() + " " +
                AppClass.getInstance().getUserData().getAccessToken();

//        String header = "bearer 4TP4rvJ___LzHlIRsqbR1TuA7Ej3AaiU0J5K6EHCnYfQxsIYX0jAz8mXFmSoDkXodfa_gZVAEJu2FVXEihC-wF18C1qYNddcKNJDJiuSzcIC_YFqotYWXHl8Mwd3PXcJhLrsOOioyKAuihUr0et1U_QP0Ypgaqne00uTbU1K9ZGrQLKLsVP8z3aHtW6qUIyOwidsOY3c0rtdvy0GfNHy71GbN6XXcZX8fkcMwndubgkhYZVZJg_P2RS8p3I6W3p0KU-2Y1ifxn8WQdxljK_VEN8VvtVQjFiJgsEoW5Jvq3uvakYHDlLR-CfvMk1wLFqQqG9RJOOGKpVhdwcihg21QiT3wrLWcptBTjPsbRB2No8sH8IixnBo8FpNvAhSl_bTDf58atdOEcNxGOXPNuJvrh3aYmi2BXaQkuqSO10gskgMdweGgljpG5WjwXw2Jg6tBpblNMad62m9R4yDVg6b4KZMxLz9DRzqPr0fyo6cSxng9lT4OE-ra6MRpXjNTdFJq-jwuJQxb3DwGeB9gZcTO1R1_6HrcjhdjwOr4Wsi0KcW5D_-bYWTyIqQHfxWlg7szw66sg";
        ApiClient.getClient(getActivity()).create(ApiInterface.class).
                loadCustomers(/*"application/json",*/header,params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                System.out.println("response:"+response);
                CustomLoder.dismisCustomProgressBar();
                try {
                    String responseBody=response.body().string();
                    System.out.println("responseBody:"+responseBody);

                    if(response.code() == 200){
                        ListResponse result = new Gson().fromJson(responseBody,ListResponse.class);

                        CustomerFragServer.this.customers.clear();
                        CustomerFragServer.this.customers.addAll(result.getPayload());
                        setUI(CustomerFragServer.this.customers);

                    } else {

                    }
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



