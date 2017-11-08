package com.example.pannawatnokket.findmycat;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pannawatnokket.findmycat.adapter.GameAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private RoundCornerProgressBar timeProgressBar;

    private GameAdapter gameAdapter;
    private ArrayList<Integer> imageIntegerArrayList;
    private int columns;
    private float time;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        columns = 2;
        setUI();
        initTimeProgress();
        setListener();
        nextLevel();
    }

    private void setUI() {
        gridView = (GridView) findViewById(R.id.grid_view);
        timeProgressBar = (RoundCornerProgressBar) findViewById(R.id.time_progress);
    }

    private void initTimeProgress() {
        time = 10;
        timeProgressBar.setProgressColor(Color.parseColor("#4CA59D"));
        timeProgressBar.setProgressBackgroundColor(Color.parseColor("#E7E7E9"));
        timeProgressBar.setMax(time);
        timeProgressBar.setProgress(time);
    }

    private void setListener() {
        gridView.setOnItemClickListener(this);
    }

    private void nextLevel() {
        imageIntegerArrayList = new ArrayList<>();
        for (int i = 0; i < columns * columns; i++) {
            imageIntegerArrayList.add(1);
        }
        gameAdapter = new GameAdapter(MainActivity.this, imageIntegerArrayList, columns);
        gridView.setAdapter(gameAdapter);
        gridView.setNumColumns(columns);
        countTime();
    }


    private void countTime() {
        countDownTimer = new CountDownTimer(5000, 1) {
            public void onTick(long millisUntilFinished) {
                time = (float) (time - 0.037);
                if (time < 5) {
                    timeProgressBar.setProgressColor(Color.parseColor("#d65920"));
                    timeProgressBar.setProgressBackgroundColor(Color.parseColor("#E7E7E9"));
                }
                timeProgressBar.setProgress(time);
            }

            public void onFinish() {
                timeProgressBar.setProgress(0);
                Toast.makeText(MainActivity.this, "time out", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        columns++;
        countDownTimer.cancel();
        initTimeProgress();
        nextLevel();
    }
}
