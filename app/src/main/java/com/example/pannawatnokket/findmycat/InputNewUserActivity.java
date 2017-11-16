package com.example.pannawatnokket.findmycat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class InputNewUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_user);

        ImageView nextbtn = (ImageView) findViewById(R.id.nextBtn);
        ImageView backbtn = (ImageView) findViewById(R.id.backBtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputNewUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputNewUserActivity.this, ChooseUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
