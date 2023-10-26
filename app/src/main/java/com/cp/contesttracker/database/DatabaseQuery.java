package com.cp.contesttracker.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQuery {
    private Context context;
    private static final String TABLE_NAME = "notification";

    public DatabaseQuery(Context context) {
        this.context = context;
    }

    public void insertNotificationSchedule(String contestId, int minutesAhead) {
        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contest_id", contestId);
        contentValues.put("minutes_ahead", minutesAhead);
        try{
            id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }
//        return id;
    }

    public void deleteNotificationSchedule(String contestId, int minutesAhead) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            sqLiteDatabase.delete(TABLE_NAME, "contest_id=? AND minutes_ahead=?",
                    new String[]{contestId, String.valueOf(minutesAhead)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }
    }

    public List<String> getAllSchedule(String contestId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        List<String> list = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME, null,
                    "contest_id=?", new String[]{contestId}, null, null, null);
            if(cursor != null)
            {
                while (cursor.moveToNext())
                {
                    String minutesAhead = String.valueOf(cursor.getInt(cursor.getColumnIndex("minutes_ahead")));
                    list.add(minutesAhead + " minutes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return list;
    }
}
