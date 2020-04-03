package com.dailysaver.shadowhite.dailysaver.utills;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.dailysaver.shadowhite.dailysaver.R;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class Tools {
    private Context context;

    public Tools(Context context) {
        this.context = context;
    }

    /**
     * This method will provide animation
     *
     * @param view
     */
    public void setAnimation(View view){
        Animation a = AnimationUtils.loadAnimation(context, R.anim.fadein);
        view.startAnimation(a);
    }

    /**
     * This method will provide the date formatter
     */
    private DateFormat getAndroidDateFormat(){
        DateFormat dateFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        }
        return dateFormatter;
    }

    /**
     * This method will convert string to a date value
     *
     * @param strDate
     */
    public Date convertStrToDate(String strDate){
        Date date = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                date = getAndroidDateFormat().parse(strDate);
                System.out.println("Current Date Time : " + date);
            }
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * This method will convert date to a string value
     *
     * @param date
     */
    public String dateToStr(Date date){
        String dateStr = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateStr = getAndroidDateFormat().format(date);
        }
        System.out.println("Current Date Time : " + dateStr);

        return dateStr;
    }

    /**
     * This method will convert date to a long value
     *
     * @param date
     */
    public Long convertDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    /**
     * This method will convert long to a date value
     *
     * @param dateInLong
     */
    public String longToDateString(long dateInLong){
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dateString = getAndroidDateFormat().format(new Date(dateInLong));
        }
        return dateString;
    }

    /**
     * This method will exit the app
     */
    public void exitApp(){
        Intent exitIntent = new Intent(Intent.ACTION_MAIN);
        exitIntent.addCategory(Intent.CATEGORY_HOME);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(exitIntent);
    }
}
