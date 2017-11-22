package com.example.pannawatnokket;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pannawatnokket.findmycat.R;

/**
 * Created by pannawatnokket on 22/11/2017 AD.
 */

public class Utility {

    public static void showNoInternet(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }
}
