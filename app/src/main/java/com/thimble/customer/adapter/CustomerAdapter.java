package com.thimble.customer.adapter;

import android.content.Context;
//import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.R;
//import com.thimble.customer.databinding.ItemCustomersBinding;
import com.thimble.customer.db.model.Customer;


import java.util.List;


/**
 * Created by satyabrata on 29/5/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemRowHolder> {

    private List<Customer> customerList;
    private Context mContext;


    public CustomerAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public CustomerAdapter(Context mContext, List<Customer> customerList) {
        this.mContext = mContext;
        this.customerList = customerList;
    }



    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
//        ItemCustomersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_customers, viewGroup, false);
        return new ItemRowHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
//        holder.binding.setEdu(eduList.get(position));position


    }



    @Override
    public int getItemCount() {
        return (null != customerList ? customerList.size() : 10);
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {

//        private ItemCustomersBinding binding;


        private ItemRowHolder(View view) {
            super(view);

        }
//        private ItemRowHolder(ItemCustomersBinding itemBinding) {
//            super(itemBinding.getRoot());
//            this.binding = itemBinding;
        }


}


