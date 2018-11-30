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
import com.thimble.customer.db.model.Customer;
import com.thimble.customer.model.Image;
import com.thimble.customer.util.CaptureImage;


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
        if(imageList.get(position).getImgUri() != null){
            String imgPath = CaptureImage.getPath(mContext, imageList.get(position).getImgUri());
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            holder.binding.imvItem.setImageBitmap(bitmap);
        }else {
//            holder.binding.imvItem.setImageBitmap(null);
        }


        holder.binding.imvItem.setOnClickListener(view -> {
            listener.onItemClick(position,imageList.get(position).getImgType());
        });

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
    }

}


