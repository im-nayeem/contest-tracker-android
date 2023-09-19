package com.cp.contesttracker;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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


//    public static Date getCurrentDate(){
//        LocalDateTime currentDate = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        String formattedDate = currentDate.format(formatter);
//        return parseTimeStamp(formattedDate);
//
//    }

    public static Date getCurrentDateInGMT() {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        return Date.from(currentDateTime.toInstant());
    }


    public static boolean isToday(Date date) {
        Date currentDate = new Date();
        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Add one day to the current date to get tomorrow's date
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // Get the Date object for tomorrow
        currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        String currentStr = dateFormat.format(currentDate);

        return dateStr.equals(currentStr);
    }

}
