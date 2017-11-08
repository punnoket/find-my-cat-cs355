package com.example.pannawatnokket.findmycat;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pannawatnokket.findmycat.adapter.GameAdapter;

import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private RoundCornerProgressBar timeProgressBar;

    private GameAdapter gameAdapter;
    private ArrayList<Integer> imageIntegerArrayList;
    private int columns;
    private int time;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        columns = 2;
        time = 10;
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
        timeProgressBar.setProgressColor(Color.parseColor("#4CA59D"));
        timeProgressBar.setProgressBackgroundColor(Color.parseColor("#E7E7E9"));
        timeProgressBar.setMax(10);
        timeProgressBar.setProgress(10);
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
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeProgressBar.setProgress(time--);
            }

            public void onFinish() {
                timeProgressBar.setProgress(time--);
                Toast.makeText(MainActivity.this, "time out", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        columns++;
        countDownTimer.cancel();
        time = 10;
        initTimeProgress();
        nextLevel();
    }
}
