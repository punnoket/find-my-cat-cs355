package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChooseScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_score);

        ImageView globalscore = (ImageView) findViewById(R.id.globalscore);
        ImageView localuser = (ImageView) findViewById(R.id.localuser);
        ImageView back = (ImageView)findViewById(R.id.backBtn);
        globalscore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScoreActivity.this, HighScoreGlobalActivity.class);
                startActivity(intent);
            }
        });
        localuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScoreActivity.this, HighScoreActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseScoreActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
