package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.pannawatnokket.findmycat.adapter.HighScoreAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import java.util.ArrayList;

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
    }
}
