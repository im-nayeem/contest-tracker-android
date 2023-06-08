package com.cp.contesttracker;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static String formatTimeStamp(String timestamp){

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM EEE HH:mm", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM '|' EEEE '|' hh:mm a", Locale.ENGLISH);

        String outputDate = null;

        try {
                Date date = inputFormat.parse(timestamp);
                outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("Error: ","Invalid date format");
        }
        return outputDate;

    }

}
