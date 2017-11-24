package com.example.pannawatnokket.findmycat.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.R;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 11/8/2017 AD.
 */

public class GameAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Integer> imageRes;
    int column;
    int width;
    int lv;
    public GameAdapter(Context context, ArrayList<Integer> imageRes, int column, int width,int lv) {
        this.mContext = context;
        this.imageRes = imageRes;
        this.column = column;
        this.width = width;
        this.lv=lv;
    }

    public int getCount() {
        return imageRes.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = mInflater.inflate(R.layout.image_view, parent, false);

        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageDrawable(mContext.getDrawable(imageRes.get(position)));

        if(lv >= 20 && lv < 40){
            final ColorMatrix matrixA = new ColorMatrix();
            matrixA.setSaturation(0);
            final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixA);
            imageView.setColorFilter(filter);

        }else if (lv >= 40){
            final ColorMatrix matrixA = new ColorMatrix();
            matrixA.setSaturation(9);
            final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixA);
            imageView.setColorFilter(filter);
        }



        int newWidth =  width/column;
        int newHeight = width/column;
        imageView.requestLayout();
        imageView.getLayoutParams().height = newHeight;
        imageView.getLayoutParams().width = newWidth;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return view;
    }
}