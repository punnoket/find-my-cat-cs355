package com.example.pannawatnokket.findmycat.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pannawatnokket.findmycat.entity.User;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.Constants.NAME;
import static com.example.Constants.SCORE;
import static com.example.Constants.TABLE_NAME;

/**
 * Created by pannawatnokket on 16/11/2017 AD.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseManager(Context context) {
        super(context, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTERGER PRIMARY KEY, "
                + NAME + " TEXT NOT NULL, "
                + SCORE + " INTERGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS events");
        onCreate(sqLiteDatabase);
    }

    public long addUser(User user) {
        long id;
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(SCORE, user.getScore());

        id = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public ArrayList<User> getAllUserOrderScore() {
        ArrayList<User> userArrayList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        String ORDER_BY = SCORE + " DESC";
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ORDER_BY,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (cursor.moveToNext()) {
            userArrayList.add(new User((int) cursor.getLong(0), cursor.getString(1), cursor.getInt(2)));
        }
        return userArrayList;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> userArrayList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (cursor.moveToNext()) {
            userArrayList.add(new User((int) cursor.getLong(0), cursor.getString(1), cursor.getInt(2)));
        }
        return userArrayList;
    }

    public void updateScore(User user) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, user.getId());
        values.put(NAME, user.getName());
        values.put(SCORE, user.getScore());

        sqLiteDatabase.update(TABLE_NAME,
                values,
                _ID + " = ? ",
                new String[]{String.valueOf(user.getId())});

        sqLiteDatabase.close();
    }

    public User getUser(long id) {
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                null,
                _ID + " = ? ",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User();
        user.setId((int) cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setScore(cursor.getInt(2));

        return user;
    }
}
