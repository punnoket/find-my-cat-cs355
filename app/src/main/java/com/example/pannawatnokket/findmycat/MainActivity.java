package com.example.pannawatnokket.findmycat;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pannawatnokket.findmycat.adapter.GameAdapter;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.sqlite.DatabaseManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;
    private RoundCornerProgressBar timeProgressBar;
    private TextView levelTextView;
    private TextView scoreTextView;
    private TextView timeTextView;
    private ImageView timeOutImageView;

    private GameAdapter gameAdapter;
    private ArrayList<Integer> imageIntegerArrayList;
    private int columns;
    private int score;
    private int level;
    private float time;
    private int time2;
    private int indexCat;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private MediaPlayer timeOutMediaPlayer;
    private MediaPlayer theamSongMediaPlayer;
    private MediaPlayer alarmMediaPlayer;
    private DatabaseManager databaseManager;
    private Animation timeOutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        databaseManager = new DatabaseManager(this);
        timeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.time_out_anim);
        columns = 2;
        score = 0;
        level = 1;
        setSound();
        setUI();
        initTimeProgress();
        setListener();
        nextLevel();
    }

    private void setUI() {
        gridView = (GridView) findViewById(R.id.grid_view);
        timeProgressBar = (RoundCornerProgressBar) findViewById(R.id.time_progress);
        levelTextView = (TextView) findViewById(R.id.level);
        scoreTextView = (TextView) findViewById(R.id.score);
        timeTextView = (TextView) findViewById(R.id.time);
        timeOutImageView = (ImageView) findViewById(R.id.time_out_image);
        scoreTextView.setText(String.valueOf(0));
    }

    private void setSound() {
        timeOutMediaPlayer = MediaPlayer.create(this, R.raw.time_out);
        alarmMediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        theamSongMediaPlayer = MediaPlayer.create(this, R.raw.theme_sound);
        theamSongMediaPlayer.start();
        theamSongMediaPlayer.setLooping(true);
    }

    private void initTimeProgress() {
        time = 10;
        time2 = 10;
        timeProgressBar.setProgressColor(Color.parseColor(getString(R.string.progress_color)));
        timeProgressBar.setProgressBackgroundColor(Color.parseColor(getString(R.string.background_progress_color)));
        timeProgressBar.setMax(time);
        timeProgressBar.setProgress(time);
    }

    private void setListener() {
        gridView.setOnItemClickListener(this);
    }

    private void nextLevel() {
        levelTextView.setText(String.valueOf(level));
        level++;
        imageIntegerArrayList = new ArrayList<>();
        Random ran = new Random();
        indexCat = ran.nextInt(columns * columns);
        for (int i = 0; i < columns * columns; i++) {
            String name = "d" + (ran.nextInt(39) + 1);

            int resource = getResources().getIdentifier(name, "drawable", getPackageName());

            imageIntegerArrayList.add(resource);
        }
        imageIntegerArrayList.set(indexCat, R.drawable.cat);
        gameAdapter = new GameAdapter(MainActivity.this, imageIntegerArrayList, columns);
        gridView.setAdapter(gameAdapter);
        gridView.setNumColumns(columns);
        countTime();
        countTime2();
    }


    private void countTime() {
        countDownTimer = new CountDownTimer(10000, 1) {
            public void onTick(long millisUntilFinished) {
                time = (float) (time - 0.0175);
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
                timeOutImageView.setVisibility(View.VISIBLE);
                timeOutImageView.startAnimation(timeOutAnimation);
                alarmMediaPlayer.start();
            }
        }.start();
    }

    private void countTime2() {
        countDownTimer2 = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time2 = (time2 - 1);
                timeTextView.setText(String.valueOf(time2));
            }

            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == indexCat) {
            if (level <= 11)
                columns++;

            timeOutMediaPlayer.stop();
            countDownTimer.cancel();
            countDownTimer2.cancel();
            resetSound();
            calculateScore();
            initTimeProgress();
            nextLevel();
        } else {
            time = (float) (time - 1.0);
            time2 = (int) (time2 - 1.0);
        }
    }

    private void resetSound() {
        timeOutMediaPlayer.release();
        timeOutMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.time_out);
    }

    private void calculateScore() {
        score += time2 * 10;
        scoreTextView.setText(String.valueOf(score));
    }

    private void endGame() {
        User user = databaseManager.getUser(getIntent().getLongExtra("id", 0));
        user.setScore(score);
        databaseManager.updateScore(user);
    }
}
