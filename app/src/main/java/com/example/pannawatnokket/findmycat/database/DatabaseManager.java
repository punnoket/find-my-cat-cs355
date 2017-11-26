package com.example.pannawatnokket.findmycat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pannawatnokket.findmycat.entity.User;

import java.util.ArrayList;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.pannawatnokket.findmycat.Constants.ID_GLOBAL;
import static com.example.pannawatnokket.findmycat.Constants.NAME;
import static com.example.pannawatnokket.findmycat.Constants.SCORE;
import static com.example.pannawatnokket.findmycat.Constants.TABLE_NAME;

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
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( "
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT NOT NULL, "
                + ID_GLOBAL + " TEXT, "
                + SCORE + " INTERGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS events");
        onCreate(sqLiteDatabase);
    }

    public long createUser(User user, Context context, int in) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(SCORE, user.getScore());
        values.put(ID_GLOBAL, user.getIdGlobal());
        long id = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public ArrayList<User> getAllUserOrderScore() {
        ArrayList<User> userArrayList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + SCORE + " DESC", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setIdGlobal(cursor.getString(2));
                user.setScore(cursor.getInt(3));
                userArrayList.add(user);
            }
        }
        sqLiteDatabase.close();
        return userArrayList;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> userArrayList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setIdGlobal(cursor.getString(2));
                user.setScore(cursor.getInt(3));
                userArrayList.add(user);
            }
        }
        sqLiteDatabase.close();
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

    public void deleteUser(long id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, _ID + " = " + id, null);
        sqLiteDatabase.close();
    }

    public User getUserById(long id) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " where " + _ID + "='" + id + "'", null);
        if (cursor.moveToFirst() && cursor != null) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setIdGlobal(cursor.getString(2));
            user.setScore(cursor.getInt(3));
            return user;
        }
        sqLiteDatabase.close();
        return null;
    }
}
