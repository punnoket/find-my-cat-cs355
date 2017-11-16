package com.example.pannawatnokket.findmycat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChooseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);

        ImageView newuser = (ImageView) findViewById(R.id.newUser);
        ImageView olduser = (ImageView) findViewById(R.id.oldUser);
        ImageView backbtn = (ImageView) findViewById(R.id.backBtn);
        newuser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUserActivity.this, InputNewUserActivity.class);
                startActivity(intent);
            }
        });
        olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUserActivity.this, PlaygameActivity.class);
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
