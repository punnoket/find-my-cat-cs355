package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pannawatnokket.findmycat.adapter.OldUserAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.database.DatabaseManager;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserListActivity extends Activity {
    private ListView listView;
    private ArrayList<User> userArrayList;
    private OldUserAdapter oldUserAdapter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        dialog = new Dialog(UserListActivity.this);

        userArrayList = new DatabaseManager(this).getAllUser();
        listView = findViewById(R.id.listview);

        oldUserAdapter = new OldUserAdapter(UserListActivity.this, userArrayList);
        listView.setAdapter(oldUserAdapter);

        ImageView backbtn = (ImageView) findViewById(R.id.backBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this, ChooseUserActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;


            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
