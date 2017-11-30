package com.moblieapp.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.moblieapp.pannawatnokket.findmycat.adapter.OldUserAdapter;
import com.moblieapp.pannawatnokket.findmycat.entity.User;
import com.moblieapp.pannawatnokket.findmycat.database.DatabaseManager;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserListActivity extends Activity {
    private ListView listView;
    private ArrayList<User> userArrayList;
    private OldUserAdapter oldUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
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

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserListActivity.this, ChooseUserActivity.class);
        startActivity(intent);
    }
}
