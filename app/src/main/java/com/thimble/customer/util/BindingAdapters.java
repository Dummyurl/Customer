package com.thimble.customer.util;

import android.databinding.BindingAdapter;
import android.widget.EditText;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thimble.customer.R;
import com.thimble.customer.rest.ApiClient;


/**
 * Created by satyabrata on 16/7/18.
 */

public class BindingAdapters {



    @BindingAdapter({"addTextChangeListener"})
    public static void addTextChangeListener(EditText editText, String errMsg) {
//        editText.addTextChangedListener(new GenericTextWatcher(editText,errMsg));
    }


    @BindingAdapter("imageUrl")
    public static void loadItemImage(ImageView view, String imageUrl) {
        if(imageUrl != null) return;
        if(imageUrl.contains("null")) return;
        if (imageUrl.isEmpty()) return;

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_placeholder);
        requestOptions.error(R.drawable.ic_image_placeholder);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(ApiClient.BASE_URL_IMG + imageUrl)
                .into(view);

    }

}
