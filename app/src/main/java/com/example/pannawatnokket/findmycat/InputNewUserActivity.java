package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pannawatnokket.Utility;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InputNewUserActivity extends Activity {
    private TextView nameTextView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_input_new_user);
        nameTextView = (TextView) findViewById(R.id.inputName);

        ImageView nextbtn = (ImageView) findViewById(R.id.nextBtn);
        ImageView backbtn = (ImageView) findViewById(R.id.backBtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(InputNewUserActivity.this, MainActivity.class);
                if (nameTextView.getText().length() != 0) {
                    createUser();
                    startActivity(intent);
                } else {
                    Utility.shoeDialog(InputNewUserActivity.this,
                            getResources().getString(R.string.error),
                            getResources().getString(R.string.plseEnterYourname));
                }
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

    private void createUser() {
        long id = new DatabaseManager(this).addUser(new User(0, nameTextView.getText().toString(), 0));
        intent.putExtra("id", id);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
