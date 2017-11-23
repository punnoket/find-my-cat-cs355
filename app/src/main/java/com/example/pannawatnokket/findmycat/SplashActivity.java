package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;

import com.example.pannawatnokket.findmycat.database.FirebaseManager;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.listener.OnSuccessListener;

import java.util.ArrayList;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new FirebaseManager().getAllUserHighScore(new OnSuccessListener() {
            @Override
            public boolean isSuccess(boolean isSuccess) {
                return isSuccess;
            }

            @Override
            public ArrayList<User> getUser(ArrayList<User> userArrayList) {
                return userArrayList;
            }
        });

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);

            }

        }.start();

    }

}
