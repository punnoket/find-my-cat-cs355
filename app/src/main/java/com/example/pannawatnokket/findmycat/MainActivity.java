package com.example.pannawatnokket.findmycat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pannawatnokket.findmycat.adapter.GameAdapter;
import com.example.pannawatnokket.findmycat.database.FirebaseManager;
import com.example.pannawatnokket.findmycat.entity.User;
import com.example.pannawatnokket.findmycat.database.DatabaseManager;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
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
    private int width;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer2;
    private MediaPlayer timeOutMediaPlayer;
    private MediaPlayer theamSongMediaPlayer;
    private MediaPlayer alarmMediaPlayer;
    private DatabaseManager databaseManager;
    private FirebaseManager firebaseDatabase;
    private Animation timeOutAnimation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        databaseManager = new DatabaseManager(this);
        firebaseDatabase = new FirebaseManager();
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        gridView.getLayoutParams().height = width;
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
        gameAdapter = new GameAdapter(MainActivity.this, imageIntegerArrayList, columns, width);
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

                if (time <= 0)
                    endGame();

                timeProgressBar.setProgress(time);
            }

            public void onFinish() {
                endGame();
            }
        }.start();
    }

    private void countTime2() {
        countDownTimer2 = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                time2 = (time2 - 1);
                timeTextView.setText(String.valueOf(time2));
                if (time2 <= 0) {
                    timeTextView.setText(String.valueOf(0));
                }
            }

            public void onFinish() {

            }
        }.start();
    }

    private void countTime3() {
        countDownTimer2 = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("id", getIntent().getLongExtra("id", 0));
                intent.putExtra("isShow", true);
                startActivity(intent);
                finish();
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
            if (time2 == 0 && time == 0) {
                stopTime();
                countDownTimer.onFinish();
                countDownTimer2.onFinish();
            }
        }
    }

    private void stopTime() {
        countDownTimer.cancel();
        countDownTimer2.cancel();
    }

    private void resetSound() {
        timeOutMediaPlayer.release();
        timeOutMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.time_out);
    }

    private void stopSound() {
        timeOutMediaPlayer.stop();
        theamSongMediaPlayer.stop();
    }

    private void calculateScore() {
        score += time2 * 10;
        scoreTextView.setText(String.valueOf(score));
    }

    private void endGame() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        user = databaseManager.getUserById(getIntent().getLongExtra("id", 0));

        if (score >= user.getScore())
            user.setScore(score);

        timeTextView.setText("0");
        databaseManager.updateScore(user);

        resetSound();
        showTimeOut();
        stopSound();
        stopTime();
        countTime3();
    }

    private void showTimeOut() {
        timeProgressBar.setProgress(0);
        timeOutImageView.setVisibility(View.VISIBLE);
        timeOutImageView.startAnimation(timeOutAnimation);
        alarmMediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopSound();
        stopTime();
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTime();
        stopSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTime();
        stopSound();
    }
}
