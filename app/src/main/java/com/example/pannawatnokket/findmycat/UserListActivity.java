package com.example.pannawatnokket.findmycat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.adapter.HighScoreAdapter;
import com.example.pannawatnokket.findmycat.adapter.OldUserAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserListActivity extends AppCompatActivity {
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                TextView deleteTextView = view.findViewById(R.id.delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatabaseManager(UserListActivity.this).deleteUser(userArrayList.get(index).getId());
                        finish();
                        startActivity(getIntent());                    }
                });

                LinearLayout lineer = view.findViewById(R.id.lineer);
                lineer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UserListActivity.this, MainActivity.class);
                        intent.putExtra("id", userArrayList.get(index).getId());
                        startActivity(intent);
                    }
                });

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
