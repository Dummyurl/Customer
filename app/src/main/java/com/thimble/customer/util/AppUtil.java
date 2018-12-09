package com.thimble.customer.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thimble.customer.R;

public class AppUtil {

    public static void loadImage(ImageView view, String imageUrl) {
        if(imageUrl == null) return;
        if (imageUrl.isEmpty()) return;
        if(imageUrl.contains("null")) return;


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_placeholder);
        requestOptions.error(R.drawable.ic_image_placeholder);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl)
                .into(view);

    }


}
