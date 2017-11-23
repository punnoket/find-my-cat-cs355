package com.example.pannawatnokket.findmycat.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.MainActivity;
import com.example.pannawatnokket.findmycat.R;
import com.example.pannawatnokket.findmycat.UserListActivity;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.database.DatabaseManager;

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

        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater mInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null)
                view = mInflater.inflate(R.layout.olduser_view, parent, false);

            TextView index = (TextView) view.findViewById(R.id.indexScore);
            TextView name = (TextView) view.findViewById(R.id.nameScore);

            index.setText(String.valueOf(position+1));
            name.setText(userArrayList.get(position).getName());

            LinearLayout deleteLinearLayout = view.findViewById(R.id.delete);
            deleteLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(userArrayList.get(position).getId());
                }
            });

            LinearLayout userLinearLayout = view.findViewById(R.id.user);
            userLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("id", userArrayList.get(position).getId());
                    mContext.startActivity(intent);
                }
            });

            return view;
        }

    private void showDialog(final long id) {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseManager(mContext).deleteUser(id);
                Intent intent = new Intent(mContext, UserListActivity.class);
                mContext.startActivity(intent);

            }
        });

        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
