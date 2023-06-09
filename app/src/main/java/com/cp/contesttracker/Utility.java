package com.cp.contesttracker;

import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public class Utility {
    public static Date parseTimeStamp(String timestamp){



        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM EEE HH:mm", Locale.ENGLISH);

        Date outputDate = null;

        try {
                outputDate = inputFormat.parse(timestamp);

        } catch (ParseException e) {
            Log.e("Error: ","Invalid date format");
        }
        return outputDate;

    }

    public static String formatTimeStamp(Date timeStamp){
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM '|' EEEE '|' hh:mm a", Locale.ENGLISH);
        return outputFormat.format(timeStamp);
    }

    public static Boolean ifDateIsOver(Date deadline, Date currentDate){
        if(deadline.compareTo(currentDate) < 1)
            return true;
        return false;
    }


    public static Date getCurrentDate(){
        LocalDateTime currentDate = LocalDateTime.now();
        return parseTimeStamp(currentDate.toString());

    }

}
