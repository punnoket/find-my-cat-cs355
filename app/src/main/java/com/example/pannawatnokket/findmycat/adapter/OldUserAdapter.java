package com.example.pannawatnokket.findmycat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.R;
import com.example.pannawatnokket.findmycat.entity.User;

import java.util.ArrayList;

/**
 * Created by gminemini on 11/16/2017 AD.
 */

public class OldUserAdapter extends BaseAdapter{

        Context mContext;
        ArrayList<User> userArrayList;

        public OldUserAdapter(Context context, ArrayList<User> userArrayList) {
            this.mContext = context;
            this.userArrayList = userArrayList;
        }

        public int getCount() {
            return userArrayList.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater mInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null)
                view = mInflater.inflate(R.layout.olduser_view, parent, false);

            TextView index = (TextView) view.findViewById(R.id.indexScore);
            TextView name = (TextView) view.findViewById(R.id.nameScore);


            index.setText(String.valueOf(position+1));
            name.setText(userArrayList.get(position).getName());


            return view;
        }

}
