package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pannawatnokket.findmycat.adapter.HighScoreAdapter;
import com.example.pannawatnokket.findmycat.database.FirebaseManager;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.database.DatabaseManager;
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
    private Dialog dialog;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_high_score);

        user = new DatabaseManager(HighScoreActivity.this).getUserById(getIntent().getLongExtra("id", 0));
        ArrayList<User> userArrayList = new DatabaseManager(this).getAllUserOrderScore();
        ListView listView = findViewById(R.id.listview);
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this, userArrayList);
        listView.setAdapter(highScoreAdapter);

        ImageView back = (ImageView) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScoreActivity.this, ChooseScoreActivity.class);
                startActivity(intent);
            }
        });
        ImageView next = (ImageView) findViewById(R.id.nextBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScoreActivity.this, ChooseUserActivity.class);
                startActivity(intent);
            }
        });
        dialog = new Dialog(HighScoreActivity.this);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        shareDialog = new ShareDialog(this);

        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                dialog.dismiss();
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                dialog.dismiss();
            }
        });


        if (getIntent().getBooleanExtra("isShow", false)) {
            if(user.getIdGlobal()!=null)
                new FirebaseManager().updateScore(user);

            showDialog();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showDialog() {
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout done = dialog.findViewById(R.id.share);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isNetworkConnected(HighScoreActivity.this)) {
                    showDialogSharedFacebook();
                    dialog.dismiss();
                } else {
                    Utility.showDialog(HighScoreActivity.this,
                            getResources().getString(R.string.error),
                            getResources().getString(R.string.check_internet));
                }
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
        TextView scoreTextView = dialog.findViewById(R.id.score);
        scoreTextView.setText(scoreTextView.getText().toString() + " "
                + getIntent().getIntExtra("score", 0) + " "
                + getResources().getString(R.string.score_thai));
    }

    private void showDialogSharedFacebook() {
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "books.book")
                .putString("og:title", "คุณได้ " + getIntent().getIntExtra("score", 0) + getResources().getString(R.string.score_thai))
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
    }
}
