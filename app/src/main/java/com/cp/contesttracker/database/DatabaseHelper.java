package com.cp.contesttracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cp-tracker-db";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper != null)
            return databaseHelper;
        else
            return new DatabaseHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE notification(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "contest_id VARCHAR(20) NOT NULL," +
                "minutes_ahead INTEGER NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
