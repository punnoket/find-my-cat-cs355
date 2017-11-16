package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.pannawatnokket.findmycat.adapter.HighScoreAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HighScoreActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_high_score);

        ArrayList<User> userArrayList = new DatabaseManager(this).getAllUserOrderScore();
        ListView listView = findViewById(R.id.listview);
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this, userArrayList);
        listView.setAdapter(highScoreAdapter);

        ImageView back = (ImageView)findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScoreActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
