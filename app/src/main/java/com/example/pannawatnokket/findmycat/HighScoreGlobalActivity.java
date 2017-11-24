package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pannawatnokket.findmycat.database.FirebaseManager;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.listener.OnSuccessListener;

import java.util.ArrayList;

public class HighScoreGlobalActivity extends Activity {
    private ArrayList<User> userArrayList;
    private ProgressDialog progressDialog;


    OnSuccessListener listener = new OnSuccessListener() {
        @Override
        public void isSuccess(boolean isSuccess) {

        }

        @Override
        public void getUser(ArrayList<User> userArrayList) {
            setUI(userArrayList);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_global);
        initProgressDialog();
        getData();
    }

    private void getData() {
        progressDialog.show();
        new FirebaseManager().getAllUserHighScore(listener);
    }

    private void setUI(ArrayList<User> userArrayList) {
        //TODO get score


        progressDialog.dismiss();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(HighScoreGlobalActivity.this);
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
}
