package com.example.pannawatnokket;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.R;

/**
 * Created by pannawatnokket on 22/11/2017 AD.
 */

public class Utility {

    public static void shoeDialog(Context context, String title, String subTitle) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);
        TextView subTitleTextView = dialog.findViewById(R.id.detail);

        titleTextView.setText(title);
        subTitleTextView.setText(subTitle);

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
