package com.thimble.customer.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ItemImagesBinding;
import com.thimble.customer.db.model.Customer;


import java.util.List;


/**
 * Created by satyabrata on 29/5/17.
 */

public class ShowImgAdapter extends RecyclerView.Adapter<ShowImgAdapter.ItemRowHolder> {

    private List<Customer> customerList;
    private Context mContext;


    public ShowImgAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public ShowImgAdapter(Context mContext, List<Customer> customerList) {
        this.mContext = mContext;
        this.customerList = customerList;
    }



    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemImagesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_images, viewGroup, false);
        return new ItemRowHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
//        holder.binding.setEdu(eduList.get(position));position

    }



    @Override
    public int getItemCount() {
        return (null != customerList ? customerList.size() : 3);
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {

        private ItemImagesBinding binding;

        private ItemRowHolder(View view) {
            super(view);
        }

        private ItemRowHolder(ItemImagesBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

}


