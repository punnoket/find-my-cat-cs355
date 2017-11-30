package com.moblieapp.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChooseUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
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
                Intent intent = new Intent(ChooseUserActivity.this, UserListActivity.class);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChooseUserActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
