package com.thimble.customer.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ItemCustomersBinding;
import com.thimble.customer.db.model.Customer;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by satyabrata on 29/5/17.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemRowHolder> implements Filterable {

    private List<Customer> customerList,mFilteredList;
    private Context mContext;
    private OnItemClickListner listner;


    public CustomerAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public CustomerAdapter(Context mContext, OnItemClickListner listner, List<Customer> customerList) {
        this.mContext = mContext;
        this.customerList = customerList;
        this.mFilteredList = customerList;
        this.listner = listner;
    }



    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemCustomersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_customers, viewGroup, false);
        return new ItemRowHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
//        holder.binding.setEdu(eduList.get(position));position

        holder.binding.tvCustName.setText(mFilteredList.get(position).getUserName());

        if(mFilteredList.get(position).isSelect()){
            holder.binding.imvSelected.setVisibility(View.VISIBLE);
            holder.binding.listItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.list_selected_color));
        }else {
            holder.binding.imvSelected.setVisibility(View.GONE);
            holder.binding.listItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.list_disselected_color));
        }

        holder.binding.listItem.setOnLongClickListener(view -> {
            listner.onLongClick(position);
            return false;
        });

        holder.binding.listItem.setOnClickListener(view -> {
            listner.onClick(position);
        });
    }



    @Override
    public int getItemCount() {
        return (null != mFilteredList ? mFilteredList.size() : 0);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = customerList;
                } else {
                    ArrayList<Customer> filteredList = new ArrayList<>();
                    for (Customer customer : customerList) {
                        if (customer.getUserName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(customer);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Customer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ItemRowHolder extends RecyclerView.ViewHolder {

        private ItemCustomersBinding binding;

        private ItemRowHolder(ItemCustomersBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface OnItemClickListner{
        void onClick(int position);
        void onLongClick(int position);
    }



}


