package com.example.pannawatnokket.findmycat;

import android.graphics.Color;
import android.media.MediaPlayer;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private RoundCornerProgressBar timeProgressBar;

    private GameAdapter gameAdapter;
    private ArrayList<Integer> imageIntegerArrayList;
    private int columns;
    private float time;
    private CountDownTimer countDownTimer;
    private MediaPlayer timeOutMediaPlayer;
    private MediaPlayer theamSongMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        columns = 2;
        setSound();
        setUI();
        initTimeProgress();
        setListener();
        nextLevel();
    }

    private void setUI() {
        gridView = (GridView) findViewById(R.id.grid_view);
        timeProgressBar = (RoundCornerProgressBar) findViewById(R.id.time_progress);

    }

    private void setSound() {
        timeOutMediaPlayer = MediaPlayer.create(this, R.raw.time_out);
        theamSongMediaPlayer =  MediaPlayer.create(this, R.raw.theme_sound);
        theamSongMediaPlayer.start();
        theamSongMediaPlayer.setLooping(true);
    }

    private void initTimeProgress() {
        time = 10;
        timeProgressBar.setProgressColor(Color.parseColor(getString(R.string.progress_color)));
        timeProgressBar.setProgressBackgroundColor(Color.parseColor(getString(R.string.background_progress_color)));
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
                    timeOutMediaPlayer.start();
                    timeProgressBar.setProgressColor(Color.parseColor(getString(R.string.color_time_out)));
                    timeProgressBar.setProgressBackgroundColor(Color.parseColor(getString(R.string.background_progress_color)));
                }
                timeProgressBar.setProgress(time);
            }

            public void onFinish() {
                timeOutMediaPlayer.stop();
                resetSound();
                timeProgressBar.setProgress(0);
                Toast.makeText(MainActivity.this, "time out", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        columns++;
        timeOutMediaPlayer.stop();
        countDownTimer.cancel();
        resetSound();
        initTimeProgress();
        nextLevel();
    }

    private void resetSound() {
        timeOutMediaPlayer.release();
        timeOutMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.time_out);
    }
}
