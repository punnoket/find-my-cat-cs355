package com.moblieapp.pannawatnokket.findmycat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moblieapp.pannawatnokket.findmycat.database.FirebaseManager;
import com.moblieapp.pannawatnokket.findmycat.entity.User;
import com.moblieapp.pannawatnokket.findmycat.database.DatabaseManager;
import com.moblieapp.pannawatnokket.findmycat.listener.OnDataSuccessListener;
import com.moblieapp.pannawatnokket.findmycat.listener.OnSuccessListener;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InputNewUserActivity extends Activity {
    private TextView nameTextView;
    private ProgressDialog progressDialog;
    private Intent intent;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_input_new_user);
        initProgressDialog();

        nameTextView = (TextView) findViewById(R.id.inputName);

        ImageView nextbtn = (ImageView) findViewById(R.id.nextBtn);
        ImageView backbtn = (ImageView) findViewById(R.id.backBtn);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(InputNewUserActivity.this, MainActivity.class);
                if (nameTextView.getText().length() != 0) {
                    user = new User(0, nameTextView.getText().toString(), 0);
                    if (!Utility.isNetworkConnected(InputNewUserActivity.this)) {
                        showDialog(InputNewUserActivity.this,
                                getResources().getString(R.string.error),
                                getResources().getString(R.string.offline_mode));

                    } else {
                        progressDialog.show();
                        createUserGlobal();
                    }
                } else {
                    Utility.showDialog(InputNewUserActivity.this,
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
        long id = new DatabaseManager(this).createUser(user, this, 100);
        intent.putExtra("id", id);
        progressDialog.dismiss();
        startActivity(intent);
        finish();
    }

    private void createUserGlobal() {
        new FirebaseManager().addUser(user, new OnSuccessListener() {
            @Override
            public void isSuccess(boolean isSuccess) {
                if (isSuccess)
                    getKeyCreate();
            }

            @Override
            public void getUser(ArrayList<User> userArrayList) {

            }
        });
    }

    private void getKeyCreate() {
        new FirebaseManager().getKeyCreate(new OnDataSuccessListener() {
            @Override
            public void getKey(String key) {
                user.setIdGlobal(key);
                createUser();
            }
        });
    }

    private void showDialog(Context context, String title, String subTitle) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(R.layout.dialog_offline);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView titleTextView = dialog.findViewById(R.id.title);
        TextView subTitleTextView = dialog.findViewById(R.id.detail);

        titleTextView.setText(title);
        subTitleTextView.setText(subTitle);

        LinearLayout done = dialog.findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        LinearLayout cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(InputNewUserActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InputNewUserActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
