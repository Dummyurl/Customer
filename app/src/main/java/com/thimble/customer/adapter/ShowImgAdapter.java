package com.thimble.customer.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thimble.customer.R;
import com.thimble.customer.databinding.ItemImagesBinding;
import com.thimble.customer.db.model.Image;
import com.thimble.customer.util.BitmapManager;
import com.thimble.customer.util.CaptureImage;
import com.thimble.customer.util.FileUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;


/**
 * Created by satyabrata on 29/5/17.
 */

public class ShowImgAdapter extends RecyclerView.Adapter<ShowImgAdapter.ItemRowHolder> {

    private List<Image> imageList;
    private Context mContext;
    private onItemClickListener listener;


    public ShowImgAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public ShowImgAdapter(Context mContext, List<Image> imageList) {
        this.mContext = mContext;
        this.listener = listener;
        this.imageList = imageList;
    }

    public ShowImgAdapter(Context mContext, onItemClickListener listener, List<Image> imageList) {
        this.mContext = mContext;
        this.listener = listener;
        this.imageList = imageList;
    }



    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        ItemImagesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_images, viewGroup, false);
        return new ItemRowHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
//        holder.binding.setEdu(eduList.get(position));
        if(imageList.get(position).getImgBitmap() != null){
            holder.binding.imvItem.setImageBitmap(imageList.get(position).getImgBitmap());
        }else {
//            holder.binding.imvItem.setImageBitmap(null);
        }


        holder.binding.imvItem.setOnClickListener(view -> {
            if (listener == null) return;
            listener.onItemClick(position,imageList.get(position).getImgType());
        });

        holder.binding.imbDelete.setOnClickListener(view -> {
            if (listener == null) return;
            listener.onDeleteClick(position,imageList.get(position).getImgType());
        });
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    @Override
    public int getItemCount() {
        return (null != imageList ? imageList.size() : 0);
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

    public interface onItemClickListener {
        void onItemClick(int position, String imgType);
        void onDeleteClick(int position, String imgType);
    }

}


