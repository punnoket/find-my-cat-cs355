package com.example.pannawatnokket.findmycat.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pannawatnokket.findmycat.entity.User;

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
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME+" ("+
                _ID+" INTERGER PRIMARY KEY AUTOINCREMENT, "
                +NAME+" TEXT NOT NULL, "
                +SCORE+" INTERGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS events");
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(SCORE, user.getScore());

        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }
}
