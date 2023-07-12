package com.cp.contesttracker;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utility {
    public static Date parseTimeStamp(String timestamp){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
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
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        return outputFormat.format(timeStamp);
    }

    public static Boolean ifDateIsOver(Date deadline, Date startTime) {
        if (deadline.compareTo(startTime) > 0) {
            return true;
        }
        return false;
    }


    public static Date getCurrentDate(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = currentDate.format(formatter);
        return parseTimeStamp(formattedDate);

    }

}
