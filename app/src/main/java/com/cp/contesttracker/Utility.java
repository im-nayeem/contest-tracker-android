package com.cp.contesttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utility {

    private Utility() {}

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


    public static Date getCurrentDateInGMT() {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        return Date.from(currentDateTime.toInstant());
    }


    public static boolean isToday(Date date) {
        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        String currentStr = dateFormat.format(currentDate);

        return dateStr.equals(currentStr);
    }

    public static boolean isDateMatched(String date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d2 = dateFormat.format(date2);
        return date1.equals(d2);
    }

    public static long getDateInMills(Date date) {
        // Convert to the desired local timezone
        TimeZone localTimeZone = TimeZone.getTimeZone("Asia/Dhaka");
        Calendar calendar = Calendar.getInstance(localTimeZone);
        calendar.setTime(date);

        // If time is greater than two days return -1
        if((calendar.getTimeInMillis() - System.currentTimeMillis()) / (24 * 3600000) > 2)
            return -1;

        // Get the time in milliseconds in the local timezone
        return calendar.getTimeInMillis();
    }


    public static void showDialogueMessage(Context context, String title, String details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(details)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the OK button click if needed
                        dialog.dismiss(); // Close the dialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static int getUniqueId() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int minutesOfDay = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
        int secondsOfDay = minutesOfDay*60 + calendar.get(Calendar.SECOND);

        return (month * 10 + dayOfMonth) * 100000 + secondsOfDay;
    }

}
