package com.example.pannawatnokket.findmycat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pannawatnokket.findmycat.adapter.HighScoreAdapter;
import com.example.pannawatnokket.findmycat.adapter.OldUserAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ArrayList<User> userArrayList = new DatabaseManager(this).getAllUser();
        ListView listView = findViewById(R.id.listview);
        OldUserAdapter oldUserAdapter = new OldUserAdapter(UserListActivity.this, userArrayList);
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
                                            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                                              
                                            }
                                        }
        );
    }
}
