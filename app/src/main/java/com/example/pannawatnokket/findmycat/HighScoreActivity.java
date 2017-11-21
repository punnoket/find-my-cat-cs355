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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HighScoreActivity extends Activity {
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;

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
        callbackManager = CallbackManager.Factory.create();


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showDialogSharedFacebook() {
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.book")
                .putString("og:title", "คุณได้ "+getIntent().getIntExtra("score", 0)+"คะแนน")
                .putString("og:description", "เก่งขนาดนี้ เหยี่ยวเรียกพ่อแล้วล่ะ")
                .putString("og:image", "https://www.picz.in.th/images/2017/11/22/iconhome.png")
                .putString("books:isbn", "0-553-57340-3")
                .build();
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putObject("book", object)
                .build();
        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("book")
                .setAction(action)
                .build();
        shareDialog.show(content);

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}
