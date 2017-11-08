package com.example.pannawatnokket.findmycat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pannawatnokket.findmycat.adapter.GameAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private GridView gridView;

    private GameAdapter gameAdapter;
    private ArrayList<Integer> imageIntegerArrayList;
    private int columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageIntegerArrayList = new ArrayList<>();
        columns = 2;
        setUI();
        setListener();
        nextLevel();
    }

    private void setUI() {
        gridView = (GridView) findViewById(R.id.grid_view);
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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        columns++;
        nextLevel();
    }
}
