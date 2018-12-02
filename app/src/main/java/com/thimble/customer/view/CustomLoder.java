package com.thimble.customer.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.thimble.customer.R;


/**
 * Created by satyabrata on 15/6/17.
 */

public class CustomLoder {

    public static Dialog customProgressBar;

    public static void showCustomProgressBar(Context context) {
        try {
            customProgressBar = new Dialog(context);
            customProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customProgressBar.setContentView(R.layout.dialog_custom_progress_bar);
            //View view = getLayoutInflater().inflate(R.layout.dialog_custom_progress_bar,null);
            //customProgressBar.setContentView(view);
            CircleProgressBar cir_prgrs_bar=(CircleProgressBar)customProgressBar.findViewById(R.id.cir_prgrs_bar);
            cir_prgrs_bar.setColorSchemeResources(R.color.app_theme_color,android.R.color.holo_orange_light,android.R.color.holo_red_light);
            customProgressBar.setCanceledOnTouchOutside(false);
            customProgressBar.setCancelable(false);
            customProgressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams wlmp = customProgressBar.getWindow().getAttributes();
            wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlmp.height = WindowManager.LayoutParams.MATCH_PARENT;

            customProgressBar.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void dismisCustomProgressBar() {
        if (customProgressBar != null)
            customProgressBar.dismiss();
    }

    public static boolean isShowing(){
        return customProgressBar.isShowing();
    }


}
